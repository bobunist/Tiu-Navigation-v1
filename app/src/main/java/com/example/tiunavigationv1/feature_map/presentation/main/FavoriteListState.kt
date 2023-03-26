package com.example.tiunavigationv1.feature_map.presentation.main

data class FavoriteListState(
    val favoriteBuildings: MutableList<BuildingListItemState> = mutableListOf(),
)
