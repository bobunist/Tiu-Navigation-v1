package com.example.tiunavigationv1.feature_map.presentation.map

import com.example.tiunavigationv1.feature_map.domain.model.Point

sealed class MapScreenEvent{

    data class OnChangeFloor(val floorId: Long): MapScreenEvent()

    data class SetPoint(val point: Point): MapScreenEvent()

    data class EnteredStartPoint(val text: String): MapScreenEvent()

    data class EnteredEndPoint(val text: String): MapScreenEvent()

    object OnSwapStartEndPoints: MapScreenEvent()

    object OnStartPath: MapScreenEvent()
}
