package com.example.tiunavigationv1.feature_map.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiunavigationv1.feature_map.domain.model.Building
import com.example.tiunavigationv1.feature_map.domain.use_case.MapUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.tiunavigationv1.ui.theme.light_gray
import com.example.tiunavigationv1.ui.theme.red

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val mapUseCases: MapUseCases
): ViewModel(){

    private val _searchTextFieldState = mutableStateOf(SearchTextFieldState(
        hint = "Введите адрес...", text = "", isListVisible = mutableStateOf(false)))
    val searchTextFieldState: State<SearchTextFieldState> = _searchTextFieldState

    private val _searchList = mutableStateListOf<BuildingListItemState>()
    var searchList: List<BuildingListItemState> = _searchList

    private val _favoriteList = mutableStateListOf<BuildingListItemState>()
    var favoriteList: List<BuildingListItemState> = _favoriteList

    private var getBuildingJob: Job? = null
    private var getFavoriteListJob: Job? = null


    init {
        getFavoriteList()
    }

    init {
        getBuilding("")
    }


    fun onEvent(event: MainScreenEvent){
        when(event){
//            is MainScreenEvent.ChangeAddressFocus -> {
//                _address.value = address.value.copy(
//                    isHintVisible = (event.focusState.isCaptured) && (address.value.text != _address.value.hint)
//                )
//
//            }
            is MainScreenEvent.EnteredAddress -> {
                _searchTextFieldState.value = searchTextFieldState.value.copy(
                    text = event.address,
                    isListVisible = mutableStateOf(event.address.isNotBlank())
                )
                getBuilding(searchTextFieldState.value.text)
            }
            is MainScreenEvent.ReverseIsFavoriteField -> {
                viewModelScope.launch {
                    reverseIsFavoriteField(event.buildingId)
//                    getBuilding(address.value.text)
                }
            }
        }
    }

    private fun reverseIsFavoriteField(id: Long){
        viewModelScope.launch { mapUseCases.reverseIsFavoriteField(id) }
    }

    private fun getFavoriteList(){
        getFavoriteListJob?.cancel()
        getFavoriteListJob = mapUseCases.getFavoriteList()
            .onEach {
                    buildings ->
                val buildingListItems = createBuildingListItems(buildings)
                _favoriteList.clear()
                _favoriteList.addAll(buildingListItems)
            }.launchIn(viewModelScope)
    }

    private fun createBuildingListItems(buildings: List<Building>): List<BuildingListItemState> {
        return buildings.map { building ->
            BuildingListItemState(
                building = building,
                color = mutableStateOf(if (building.isFavorite) red else light_gray),

                )
        }
    }

    private fun getBuilding(address: String){
        if (address.isNullOrBlank()){
            _searchList.clear()
            return
        }
        else{
            getBuildingJob?.cancel()
            getBuildingJob = mapUseCases.searchBuilding(address)
                .onEach {
                        buildings ->
                    val buildingListItems = createBuildingListItems(buildings)
                    _searchList.clear()
                    _searchList.addAll(buildingListItems)
                }.launchIn(viewModelScope)
        }
    }
}