package com.example.tiunavigationv1.feature_map.presentation.map

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiunavigationv1.feature_map.domain.model.Floor
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

    val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _startPointState = PointOfRouteState(
        mutableStateOf(""),
        "Где вы...",
        mutableStateOf(null)
    )
    val startPointState: PointOfRouteState = _startPointState

    private val _endPointState = PointOfRouteState(
        mutableStateOf(""),
        "Куда вам нужно попасть...",
        mutableStateOf(null)
    )
    val endPointState: PointOfRouteState = _endPointState

    private val _searchListState = SearchListState()
    val searchListState: SearchListState = _searchListState

    private var loadFloorJob: Job? = null

    init {
        getCurrentBuildingId(savedStateHandle)
        if (currentBuildingId != -1L){
            viewModelScope.launch {
                getFloorsByBuilding(currentBuildingId!!)
                loadFloor()
                Log.d("loadFloor", _floorState.value.points.size.toString())
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
                viewModelScope.launch { loadFloor() }

            }
            is MapScreenEvent.EnteredStartPoint -> {
                _startPointState.text.value = event.text
                _searchListState.isStartList.value = true
                if (_startPointState.text.value.isNotBlank()){
                    viewModelScope.launch {
                        getSearchListOfPoints(_startPointState.text.value,
                            _searchListState.searchList)
                        _searchListState.isSearchListVisible.value = true
                    }
                }
                else _searchListState.searchList.clear()


            }
            is MapScreenEvent.EnteredEndPoint -> {
                _endPointState.text.value = event.text
                _searchListState.isStartList.value = false
                if (_endPointState.text.value.isNotBlank()){
                    viewModelScope.launch {
                        getSearchListOfPoints(_endPointState.text.value,
                            _searchListState.searchList)
                        _searchListState.isSearchListVisible.value = true
                    }
                }
                else _searchListState.searchList.clear()

            }
            is MapScreenEvent.SetPoint -> {
                if (_searchListState.isStartList.value) {
                    _startPointState.point.value = event.point.copy()
                    _startPointState.point.value = event.point.copy()
                    _startPointState.text.value = event.point.pointName.toString()
                } else {
                    _endPointState.point.value = event.point.copy()
                    _endPointState.text.value = event.point.pointName.toString()
                }
                _searchListState.searchList.clear()
                _searchListState.isSearchListVisible.value = false
            }
            is MapScreenEvent.OnStartPath -> {

            }
            is MapScreenEvent.OnSwapStartEndPoints -> {
                val copy = _startPointState.point.value?.copy()
                _startPointState.point.value = _endPointState.point.value
                _endPointState.point.value = copy
            }
        }
    }

    private suspend fun loadFloor(){
            getPaths()
            getPoints()
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
                    viewModelScope.launch { loadFloor() }
                }
            }
        }
    }




    private suspend fun getPaths(){
    _floorState.value.paths = mapUseCases.getPathsOfFloor(floorState.value.currentFloor?.floorId!!)
}


private suspend fun getPoints(){
        _floorState.value.points = mapUseCases.getPointsOfFloor(floorState.value.currentFloor?.floorId!!)

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


