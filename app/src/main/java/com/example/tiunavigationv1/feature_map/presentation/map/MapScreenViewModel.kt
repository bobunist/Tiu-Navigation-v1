package com.example.tiunavigationv1.feature_map.presentation.map

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiunavigationv1.feature_map.domain.model.Edge
import com.example.tiunavigationv1.feature_map.domain.model.Floor
import com.example.tiunavigationv1.feature_map.domain.model.Node
import com.example.tiunavigationv1.feature_map.domain.model.Point
import com.example.tiunavigationv1.feature_map.domain.use_case.MapUseCases
import com.example.tiunavigationv1.feature_map.domain.util.PathType
import com.example.tiunavigationv1.feature_map.domain.util.PointParameter
import com.example.tiunavigationv1.feature_map.domain.util.PointType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject
import kotlin.math.sqrt

@HiltViewModel
class MapScreenViewModel@Inject constructor(
    private val mapUseCases: MapUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private var currentBuildingId: Long? = null

    private val _floorsList = mutableStateListOf<Floor>()
    val floorsList: List<Floor> = _floorsList

    private val _floorState = MutableStateFlow(
        FloorState(
            currentFloor = null,
            paths = listOf(),
            points = listOf()
        )
    )
    val floorState: StateFlow<FloorState> = _floorState.asStateFlow()

    private val _searchListState = SearchListState()
    val searchListState: SearchListState = _searchListState


    init {
        getCurrentBuildingId(savedStateHandle)
        if (currentBuildingId != -1L){
            viewModelScope.launch {
                getFloorsByBuilding(currentBuildingId!!)
                loadFloor()
            }
        }
    }

    fun onEvent(event: MapScreenEvent){
        when(event){
            is MapScreenEvent.OnChangeFloor -> {
                val floor = floorsList.find { it.floorId == event.floorId }
                if (floor != null) {
                    _floorState.value = _floorState.value.copy(currentFloor = floor)
                }
                viewModelScope.launch {
                    withContext(Dispatchers.IO){
                        loadFloor()
                    }
                }
            }
            is MapScreenEvent.EnteredStartPoint -> {
                _floorState.value.startObject.text.value = event.text
                _searchListState.isStartList.value = true
                if (_floorState.value.startObject.text.value.isNotBlank()){
                    viewModelScope.launch {
                        withContext(Dispatchers.IO){
                            getSearchListOfObjects(_floorState.value.startObject.text.value)
                        }
                        _searchListState.isSearchListVisible.value = true
                    }
                }
                else _searchListState.searchList.clear()
            }
            is MapScreenEvent.EnteredEndPoint -> {
                _floorState.value.endObject.text.value = event.text
                _searchListState.isStartList.value = false
                if (_floorState.value.endObject.text.value.isNotBlank()){
                    viewModelScope.launch {
                        withContext(Dispatchers.IO) {
                            getSearchListOfObjects(_floorState.value.endObject.text.value)
                        }
                        _searchListState.isSearchListVisible.value = true
                    }
                }
                else _searchListState.searchList.clear()
            }

            is MapScreenEvent.SetPoint -> {
                if (_searchListState.isStartList.value) {
                    _floorState.value.startObject.obj.value = event.obj
                    _floorState.value.startObject.text.value = event.obj.getName()
                } else {
                    _floorState.value.endObject.obj.value = event.obj
                    _floorState.value.endObject.text.value = event.obj.getName()
                }
                _searchListState.searchList.clear()
                _searchListState.isSearchListVisible.value = false
                updateWayIfPossible()
            }

            is MapScreenEvent.OnSwapStartEndPoints -> {
                val startPointValue = _floorState.value.startObject.obj.value
                val endPointValue = _floorState.value.endObject.obj.value

                if (startPointValue != null && endPointValue != null) {
                    val startPointCopy = startPointValue::class.java.newInstance()
                    val endPointCopy = endPointValue::class.java.newInstance()

                    for (property in startPointValue::class.java.declaredFields) {
                        property.isAccessible = true
                        property.set(startPointCopy, property.get(startPointValue))
                    }

                    for (property in endPointValue::class.java.declaredFields) {
                        property.isAccessible = true
                        property.set(endPointCopy, property.get(endPointValue))
                    }

                    for (property in startPointValue::class.java.declaredFields) {
                        property.isAccessible = true
                        property.set(startPointValue, property.get(endPointCopy))
                    }

                    for (property in endPointValue::class.java.declaredFields) {
                        property.isAccessible = true
                        property.set(endPointValue, property.get(startPointCopy))
                    }

                    _floorState.value.startObject.obj.value = startPointValue
                    _floorState.value.endObject.obj.value = endPointValue
                }
            }

            is MapScreenEvent.OnMapTap -> {
                _searchListState.isSearchListVisible.value = false
                _searchListState.searchList.clear()
                val currentStartPoint = _floorState.value.startObject.obj.value
                val currentEndPoint = _floorState.value.endObject.obj.value
                val incomingValue = event.obj
                when {
                    currentStartPoint == null && currentEndPoint == null -> {
                        _floorState.value.startObject.obj.value = incomingValue
                        _floorState.value.startObject.text.value = incomingValue.getName()
                    }
                    currentStartPoint == incomingValue -> {
                        _floorState.value.startObject.obj.value = null
                        _floorState.value.startObject.text.value = ""
                    }
                    currentEndPoint == incomingValue -> {
                        _floorState.value.endObject.obj.value = null
                        _floorState.value.endObject.text.value = ""
                    }
                    currentStartPoint == null && currentEndPoint != null -> {
                        _floorState.value.startObject.obj.value = incomingValue
                        _floorState.value.startObject.text.value = incomingValue.getName()
                    }
                    currentStartPoint != null && currentEndPoint == null -> {
                        _floorState.value.endObject.obj.value = incomingValue
                        _floorState.value.endObject.text.value = incomingValue.getName()
                    }
                    else -> {
                        _floorState.value.endObject.obj.value = incomingValue
                        _floorState.value.endObject.text.value = incomingValue.getName()
                    }

                }
                updateWayIfPossible()
            }
        }
    }

    private fun updateWayIfPossible() {
        val startMapElement = _floorState.value.startObject.obj.value
        val endMapElement = _floorState.value.endObject.obj.value
        val currentFloorId = _floorState.value.currentFloor!!.floorId

        if (startMapElement != null && endMapElement != null) {
            if (startMapElement.getFloorId() == endMapElement.getFloorId()) {
                if (currentFloorId == startMapElement.getFloorId()) {
                    updateWayOnSameFloor(_floorState.value, startMapElement, endMapElement)
                } else {
                    _floorState.value.way = emptyList()
                }
            } else {
                updateWayOnDifferentFloors(_floorState.value, startMapElement, endMapElement,
                    currentFloorId!!
                )
            }
        } else {
            _floorState.value.way = emptyList()
        }
    }


    private fun updateWayOnDifferentFloors(floorState: FloorState, startMapElement: MapElement, endMapElement: MapElement, selectedFloorId: Long) {
        val startFloorId = startMapElement.getFloorId()
        val endFloorId = endMapElement.getFloorId()

        if (startFloorId == endFloorId && startFloorId == selectedFloorId) {
            val startNodeId = startMapElement.getNodeId()
            val endNodeId = endMapElement.getNodeId()

            val startNode = floorState.nodes.find { it.id == startNodeId }
            val endNode = floorState.nodes.find { it.id == endNodeId }

            if (startNode != null && endNode != null) {
                updateWay(floorState, startNode, endNode)
            }
        } else if (selectedFloorId == startFloorId || selectedFloorId == endFloorId) {
            val mapElement = if (selectedFloorId == startFloorId) startMapElement else endMapElement
            val nodeId = mapElement.getNodeId()
            val node = floorState.nodes.find { it.id == nodeId }

            if (node != null) {
                val stairs = floorState.paths.filter { it.pathType == PathType.STAIRS }
                val stairsNodes = stairs.mapNotNull { stair -> floorState.nodes.find { it.id == stair.nodeId } }

                if (stairsNodes.isNotEmpty()) {
                    val (nearestStairsNode, _) = stairsNodes.map { stairNode -> stairNode to distanceBetweenNodes(node, stairNode) }
                        .minByOrNull { (_, distance) -> distance } ?: return

                    if (selectedFloorId == startFloorId) {
                        updateWay(floorState, node, nearestStairsNode)
                    } else {
                        updateWay(floorState, nearestStairsNode, node)
                    }
                }
            }
        } else {
            floorState.way = emptyList()
        }
    }



    private fun updateWayOnSameFloor(floorState: FloorState, startMapElement: MapElement, endMapElement: MapElement) {
        val startNodeId = startMapElement.getNodeId()
        val endNodeId = endMapElement.getNodeId()

        val startNode = floorState.nodes.find { it.id == startNodeId }
        val endNode = floorState.nodes.find { it.id == endNodeId }

        if (startNode != null && endNode != null) {
            updateWay(floorState, startNode, endNode)
        } else {
            floorState.way = emptyList()
        }
    }


    private fun updateWay(floorState: FloorState, startNode: Node, endNode: Node) {
        val way = dijkstraShortestPath(floorState.nodes, floorState.edges, startNode, endNode)
        floorState.way = way
    }


    private fun dijkstraShortestPath(nodes: List<Node>, edges: List<Edge>, startNode: Node, endNode: Node): List<Node> {
        val unvisitedNodes = nodes.toMutableSet()
        val distances = nodes.associateBy({ it.id!! }, { Float.POSITIVE_INFINITY }).toMutableMap()
        val previousNodes = mutableMapOf<Long, Node?>()

        val bidirectionalEdges = edges.toMutableList()
        edges.forEach { edge ->
            bidirectionalEdges.add(Edge(fromNodeId = edge.toNodeId,
                toNodeId = edge.fromNodeId,
                id = edge.id,
                floorId = edge.floorId))
        }

        distances[startNode.id!!] = 0f

        while (unvisitedNodes.isNotEmpty()) {
            val currentNode = unvisitedNodes.minByOrNull { distances[it.id!!]!! } ?: break
            unvisitedNodes.remove(currentNode)

            if (currentNode == endNode) {
                break
            }

            val currentEdges = bidirectionalEdges.filter { it.fromNodeId == currentNode.id }
            for (edge in currentEdges) {
                val neighborNode = nodes.find { it.id == edge.toNodeId } ?: continue
                val tentativeDistance = distances[currentNode.id!!]!! + distanceBetweenNodes(currentNode, neighborNode)

                if (tentativeDistance < distances[neighborNode.id!!]!!) {
                    distances[neighborNode.id] = tentativeDistance
                    previousNodes[neighborNode.id] = currentNode
                }
            }
        }

        return buildPath(endNode, previousNodes)
    }

    private fun distanceBetweenNodes(node1: Node, node2: Node): Float {
        val dx = node1.x - node2.x
        val dy = node1.y - node2.y
        return sqrt(dx * dx + dy * dy)
    }

    private fun buildPath(endNode: Node, previousNodes: Map<Long, Node?>): List<Node> {
        val path = mutableListOf<Node>()
        var currentNode: Node? = endNode

        while (currentNode != null) {
            path.add(currentNode)
            currentNode = previousNodes[currentNode.id!!]
        }

        return path.reversed()
    }

    private suspend fun loadFloor(){
        _floorState.value.paths = mapUseCases.getPathsOfFloor(floorState.value.currentFloor?.floorId!!)
        _floorState.value.points = mapUseCases.getPointsOfFloor(floorState.value.currentFloor?.floorId!!)
        _floorState.value.edges = mapUseCases.getEdgesByFloor(floorState.value.currentFloor?.floorId!!)
        _floorState.value.nodes = mapUseCases.getNodesByFloor(floorState.value.currentFloor?.floorId!!)
    }


    private fun getCurrentBuildingId(savedStateHandle: SavedStateHandle) {
        savedStateHandle.get<Long>("buildingId")?.let { buildingId ->
            if (buildingId != -1L) {
                currentBuildingId = buildingId
            }
        }
    }


    private suspend fun getFloorsByBuilding(buildingId: Long) {
        _floorsList.clear()

        mapUseCases.getFloorsOfBuilding(buildingId).take(1).collect { floorsList ->
            _floorsList.addAll(floorsList.distinct())
            _floorsList.sortBy {
                val pattern = "[^a-zA-Z\\d]+"
                it.floorName.lowercase(Locale.getDefault()).replace(Regex(pattern), "")
            }

            if (_floorsList.isNotEmpty()) {
                val newFloor = _floorsList[0]
//                _floorState.value.currentFloor == null ||
                if (_floorState.value.currentFloor?.floorId != newFloor.floorId) {
                    _floorState.value = _floorState.value.copy(currentFloor = newFloor)
                    viewModelScope.launch {
                        withContext(Dispatchers.IO) {
                            loadFloor()
                        }
                    }
                }
            }
        }
    }


    private suspend fun getSearchListOfObjects(
        text: String
    ) {
        _searchListState.searchList.clear()
        val tempList = mutableListOf<MapElement>()

        viewModelScope.launch(Dispatchers.IO) {
            mapUseCases.getPointsByName(text, currentBuildingId!!).take(1).collect { variableList ->
                tempList.addAll(variableList.map { point -> MapElement.PointElement(point) })
            }
            mapUseCases.getPathsByName(text, currentBuildingId!!).take(1).collect { variableList ->
                tempList.addAll(variableList.map { path -> MapElement.PathElement(path) })
            }

            val sortedList = tempList.sortedBy { mapElement ->
                when (mapElement) {
                    is MapElement.PointElement -> mapElement.point.pointName
                    is MapElement.PathElement -> mapElement.path.pathName
                }
            }
            withContext(Dispatchers.Main) {
                _searchListState.searchList.addAll(sortedList)
            }
        }
    }


}
