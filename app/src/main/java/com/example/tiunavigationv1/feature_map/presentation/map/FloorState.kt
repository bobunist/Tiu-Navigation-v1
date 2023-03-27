package com.example.tiunavigationv1.feature_map.presentation.map

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.tiunavigationv1.feature_map.domain.model.*

data class FloorState(
    var currentFloor: Floor? = null,
    var paths: List<Path> = listOf(),
    var points: List<Point> = listOf(),
    var nodes: List<Node> = listOf(),
    var edges: List<Edge> = listOf(),
)

