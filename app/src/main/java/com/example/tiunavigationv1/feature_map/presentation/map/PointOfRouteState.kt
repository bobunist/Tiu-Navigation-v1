package com.example.tiunavigationv1.feature_map.presentation.map

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.tiunavigationv1.feature_map.domain.model.Path
import com.example.tiunavigationv1.feature_map.domain.model.Point

data class PointOfRouteState(
    val text: MutableState<String>,
    val hint: String,
    val obj: MutableState<MapElement?> = mutableStateOf(null),
)
