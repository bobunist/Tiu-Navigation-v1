package com.example.tiunavigationv1.feature_map.domain.use_case

data class MapUseCases(
    val getFavoriteList: GetFavoriteList,
    val reverseIsFavoriteField: ReverseIsFavoriteField,
    val getFloorsOfBuilding: GetFloorsOfBuilding,
    val searchBuilding: SearchBuilding,
    val getPointsOfFloor: GetPointsOfFloor,
    val getPathsOfFloor: GetPathsOfFloor,
    val getFloor: GetFloor,
    val getPointsByName: GetPointsByName,
    val getEdgesByFloor: GetEdgesByFloor,
    val getNodesByFloor: GetNodesByFloor,
    val getPathsByName: GetPathsByName
){
    
}