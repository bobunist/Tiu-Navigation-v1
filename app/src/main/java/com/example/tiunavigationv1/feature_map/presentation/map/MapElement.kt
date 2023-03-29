package com.example.tiunavigationv1.feature_map.presentation.map

import com.example.tiunavigationv1.feature_map.domain.model.Path
import com.example.tiunavigationv1.feature_map.domain.model.Point

sealed class MapElement {
    abstract fun getName(): String

    data class PointElement(val point: Point) : MapElement() {
        override fun getName(): String = point.pointName.toString()
        fun getCoordinates(): Pair<Float, Float> = point.x to point.y
    }

    data class PathElement(val path: Path) : MapElement() {
        override fun getName(): String = path.pathName!!
        fun getCoordinates(points: List<Point>): Pair<Float?, Float?> {
            val point = points.find { it.pathId == path.pathId }
            return point?.x to point?.y
        }
    }
}
