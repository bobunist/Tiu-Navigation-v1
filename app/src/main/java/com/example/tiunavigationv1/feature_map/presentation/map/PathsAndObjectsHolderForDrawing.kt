package com.example.tiunavigationv1.feature_map.presentation.map

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import com.example.tiunavigationv1.feature_map.domain.model.Point

data class PathsAndObjectsHolderForDrawing(
    val elevatorsPath: PathsForDrawingState = PathsForDrawingState(color = Color.Blue),
    val stairsPath: PathsForDrawingState = PathsForDrawingState(color = Color.Blue),
    val roomsPath: PathsForDrawingState = PathsForDrawingState(color = Color.Blue),
    val outerWallPath: PathsForDrawingState = PathsForDrawingState(color = Color.Blue),
    val internalWallsPath: PathsForDrawingState = PathsForDrawingState(color = Color.Blue),
    val othersPath: PathsForDrawingState = PathsForDrawingState(color = Color.Blue),

    val objects: ObjectsForDrawingState = ObjectsForDrawingState(color = Color.Red),

    val pathsMap: MutableMap<Path, com.example.tiunavigationv1.feature_map.domain.model.Path> = mutableMapOf(),
    val pointsMap: MutableMap<Offset, Point> = mutableMapOf()
)