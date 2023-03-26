package com.example.tiunavigationv1.feature_map.presentation.map

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

data class PathsForDrawingState(
    var paths: MutableList<Path> = mutableStateListOf(),
    val color: Color,
    )
