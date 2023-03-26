package com.example.tiunavigationv1.feature_map.presentation.map

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.tiunavigationv1.feature_map.domain.model.Floor
import com.example.tiunavigationv1.feature_map.domain.model.Point
import com.example.tiunavigationv1.feature_map.domain.model.Path

data class FloorState(
    var currentFloor: Floor? = null,
    var paths: List<Path> = listOf(),
    var points: List<Point> = listOf(),
    val isItFirstLoad: MutableState<Boolean> = mutableStateOf(true)
)

