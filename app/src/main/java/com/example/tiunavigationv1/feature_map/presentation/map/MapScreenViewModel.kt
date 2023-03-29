package com.example.tiunavigationv1.feature_map.presentation.map

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiunavigationv1.feature_map.domain.model.Floor
import com.example.tiunavigationv1.feature_map.domain.model.Path
import com.example.tiunavigationv1.feature_map.domain.model.Point
import com.example.tiunavigationv1.feature_map.domain.use_case.MapUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

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
                _floorState.value.startPoint.text.value = event.text
                _searchListState.isStartList.value = true
                if (_floorState.value.startPoint.text.value.isNotBlank()){
                    viewModelScope.launch {
                        withContext(Dispatchers.IO){
                            getSearchListOfPoints(_floorState.value.startPoint.text.value,
                                _searchListState.searchList)
                            _searchListState.isSearchListVisible.value = true
                        }
                    }
                }
                else _searchListState.searchList.clear()
            }
            is MapScreenEvent.EnteredEndPoint -> {
                _floorState.value.endPoint.text.value = event.text
                _searchListState.isStartList.value = false
                if (_floorState.value.endPoint.text.value.isNotBlank()){
                    viewModelScope.launch {
                        withContext(Dispatchers.IO) {
                            getSearchListOfPoints(
                                _floorState.value.endPoint.text.value,
                                _searchListState.searchList
                            )
                            _searchListState.isSearchListVisible.value = true
                        }
                    }
                }
                else _searchListState.searchList.clear()

            }
            is MapScreenEvent.SetPoint -> {
                if (_searchListState.isStartList.value) {
                    _floorState.value.startPoint.point.value = event.point.copy()
                    _floorState.value.startPoint.point.value = event.point.copy()
                    _floorState.value.startPoint.text.value = event.point.pointName.toString()
                } else {
                    _floorState.value.endPoint.point.value = event.point.copy()
                    _floorState.value.endPoint.text.value = event.point.pointName.toString()
                }
                _searchListState.searchList.clear()
                _searchListState.isSearchListVisible.value = false
            }
            is MapScreenEvent.OnStartPath -> {

            }
            is MapScreenEvent.OnSwapStartEndPoints -> {
                val copy = _floorState.value.startPoint.point.value?.copy()
                _floorState.value.startPoint.point.value = _floorState.value.endPoint.point.value
                _floorState.value.endPoint.point.value = copy
            }
            is MapScreenEvent.OnMapTap -> {
                when (event.obj) {
                    is Point -> {
                        val currentStartPoint = _floorState.value.startPoint.point.value
                        val currentEndPoint = _floorState.value.endPoint.point.value
                        val incomingValue = event.obj

                        when {
                            currentStartPoint == null && currentEndPoint == null -> {
                                _floorState.value.startPoint.point.value = incomingValue
                            }
                            currentStartPoint == incomingValue -> {
                                _floorState.value.startPoint.point.value = null
                            }
                            currentEndPoint == incomingValue -> {
                                _floorState.value.endPoint.point.value = null
                            }
                            currentStartPoint == null && currentEndPoint != null -> {
                                _floorState.value.startPoint.point.value = incomingValue
                            }
                            currentStartPoint != null && currentEndPoint == null -> {
                                _floorState.value.endPoint.point.value = incomingValue
                            }
                        }
                    }
                    is Path -> {

                    }
                }
            }
        }
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
                if (_floorState.value.currentFloor == null || _floorState.value.currentFloor?.floorId != newFloor.floorId) {
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


    private suspend fun getSearchListOfPoints(
        text: String,
        list: SnapshotStateList<Point>
    ){
        list.clear()
        mapUseCases.getPointByName(text, currentBuildingId!!).take(1).collect { variableList ->
            list.addAll(variableList)
        }
    }
}
