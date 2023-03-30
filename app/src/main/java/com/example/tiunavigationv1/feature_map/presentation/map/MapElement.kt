package com.example.tiunavigationv1.feature_map.presentation.map

import com.example.tiunavigationv1.feature_map.domain.model.Path
import com.example.tiunavigationv1.feature_map.domain.model.Point
import com.example.tiunavigationv1.feature_map.domain.util.PointType

sealed class MapElement {
    abstract fun getName(): String
    abstract fun getNodeId(): Long?

    data class PointElement(val point: Point) : MapElement() {
        override fun getName(): String = point.pointName.toString()
        override fun getNodeId(): Long? = point.nodeId
        fun getCoordinates(): Pair<Float, Float> = point.x to point.y
    }

    data class PathElement(val path: Path) : MapElement() {
        override fun getName(): String = path.pathName!!
        override fun getNodeId(): Long? = path.nodeId
        fun getCoordinates(points: List<Point>): Pair<Float?, Float?> {
            val point = points.find { it.pathId == path.pathId }
            return point?.x to point?.y
        }
    }
}


//val a = MapElement.PointElement(Point(null, 1, 1,
//    1, null, PointType.PATH, null, null, 1f, 1f, 1))
