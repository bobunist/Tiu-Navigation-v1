package com.example.tiunavigationv1.feature_map.presentation.map

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import com.example.tiunavigationv1.feature_map.domain.model.Point
import com.example.tiunavigationv1.feature_map.domain.model.Path as PathModel

data class PathsAndObjectsHolderForDrawing(
    val elevatorsPath: PathsForDrawingState = PathsForDrawingState(color = Color.Blue),
    val stairsPath: PathsForDrawingState = PathsForDrawingState(color = Color.Blue),
    val roomsPath: PathsForDrawingState = PathsForDrawingState(color = Color.Blue),
    val outerWallPath: PathsForDrawingState = PathsForDrawingState(color = Color.Blue),
    val internalWallsPath: PathsForDrawingState = PathsForDrawingState(color = Color.Blue),
    val othersPath: PathsForDrawingState = PathsForDrawingState(color = Color.Blue),

    val objects: ObjectsForDrawingState = ObjectsForDrawingState(color = Color.Magenta),

    val pathsMap: MutableMap<Path, Pair<PathModel, List<Offset>>> = mutableMapOf(),
    val pointsMap: MutableMap<Offset, Point> = mutableMapOf(),

    val activePaths: PathsForDrawingState = PathsForDrawingState(color = Color.Red),
    val activeObjects: ObjectsForDrawingState = ObjectsForDrawingState(color = Color.Red),
)