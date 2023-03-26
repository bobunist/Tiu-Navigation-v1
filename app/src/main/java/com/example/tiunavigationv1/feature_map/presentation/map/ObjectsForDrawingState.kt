package com.example.tiunavigationv1.feature_map.presentation.map

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class ObjectsForDrawingState(
    var objects: MutableList<Offset> = mutableStateListOf(),
    val color: Color
)
