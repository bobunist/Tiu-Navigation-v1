package com.example.tiunavigationv1.feature_map.presentation.map

import com.example.tiunavigationv1.feature_map.domain.model.Path
import com.example.tiunavigationv1.feature_map.domain.model.Point

sealed class MapElement {
    data class PointElement(val point: Point) : MapElement()
    data class PathElement(val path: Path) : MapElement()
}