package com.example.tiunavigationv1.feature_map.presentation.main

data class SearchListState(
    val searchList: MutableList<BuildingListItemState> = mutableListOf(),
    val isSearchListVisible: Boolean = false
)