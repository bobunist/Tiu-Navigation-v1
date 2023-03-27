package com.example.tiunavigationv1.feature_map.presentation.map.components

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.example.tiunavigationv1.feature_map.domain.model.Point
import com.example.tiunavigationv1.feature_map.domain.util.PathType
import com.example.tiunavigationv1.feature_map.domain.util.PointParameter
import com.example.tiunavigationv1.feature_map.domain.util.PointType
import com.example.tiunavigationv1.feature_map.presentation.map.FloorState
import com.example.tiunavigationv1.feature_map.presentation.map.PathsAndObjectsHolderForDrawing
import kotlinx.coroutines.*
import com.example.tiunavigationv1.feature_map.domain.model.Path as PathModel
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.*
import kotlin.math.pow
import kotlin.math.sqrt

private fun distanceBetweenPoints(a: Offset, b: Offset): Float {
    return sqrt((a.x - b.x).pow(2) + (a.y - b.y).pow(2))
}


//// Кастомный PointerInputFilter для обработки касаний
//class PointTapFilter(
//    private val points: List<Offset>,
//    private val radius: Float,
//    private val onPointTapped: (Offset) -> Unit
//) : PointerInputFilter() {
//    override fun onCancel() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onPointerEvent(
//        pointerEvent: PointerEvent,
//        pass: androidx.compose.ui.input.pointer.PointerEventPass,
//        bounds: androidx.compose.ui.geometry.Size
//    ) {
//        pointerEvent.changes.forEach { change ->
//            if (pass == androidx.compose.ui.input.pointer.PointerEventPass.Main && change.changedToUp()) {
//                val touchPoint = change.position
//                points.forEach { point ->
//                    if (distanceBetweenPoints(point, touchPoint) <= radius) {
//                        onPointTapped(point)
//                    }
//                }
//                change.consumeDownChange()
//            }
//        }
//    }
//}
//
//@Composable
//fun Map3(
//    modifier: Modifier = Modifier,
//    floorState: State<FloorState>,
//    configuration: Configuration,
//    density: Density,
//    onPointClick: (Offset) -> Unit
//) {
//    // Остальной код...
//    val pointRadius = 15f
//
//    if (isDataLoaded) {
//        val drawContent by remember(floorState.value) {
//            derivedStateOf {
//                val pathsAndObjects = initMap(floorState.value.points, floorState.value.paths, width, height)
//                pathsAndObjects
//            }
//        }
//
//        Canvas(
//            modifier = modifier
//                .aspectRatio(3 / 2f)
//                .fillMaxSize()
//                .detectTapGestures(
//                    PointTapFilter(
//                        points = drawContent.objects.objects,
//                        radius = pointRadius,
//                        onPointTapped = onPointClick
//                    )
//                )
//        ) {
//            drawIntoCanvas { canvas ->
//                // ...
//                for (point in drawContent.objects.objects) {
//                    canvas.drawCircle(
//                        center = point,
//                        radius = pointRadius,
//                        paint = Paint().apply {
//                            color = drawContent.objects.color
//                        }
//                    )
//                }
//            }
//        }
//    } else {
//        Text(text = "Загрузка...")
//    }
//}


//@Composable
//fun Map3(
//    modifier: Modifier = Modifier,
//    floorState: State<FloorState>,
//    configuration: Configuration,
//    density: Density
//) {
//    val width = with(density) { configuration.screenWidthDp.dp.toPx() }
//    val height = width / 3 * 2
//    val (isDataLoaded, setDataLoaded) = remember { mutableStateOf(false) }
//
//    LaunchedEffect(floorState.value.paths) {
//
//        setDataLoaded(false)
//        if (floorState.value.points.isNotEmpty() && floorState.value.paths.isNotEmpty()) {
//            setDataLoaded(true)
//        }
//    }
//
//    if (isDataLoaded) {
//        val drawContent by remember(floorState.value) {
//            derivedStateOf {
//                val pathsAndObjects = initMap(floorState.value.points, floorState.value.paths, width, height);
//                { drawScope: DrawScope ->
//                    drawScope.drawPoints(pathsAndObjects.objects.objects,
//                        PointMode.Points, pathsAndObjects.objects.color, 30f)
//
//                    for (point in pathsAndObjects.objects.objects)
//                        drawScope.drawCircle(
//                            pathsAndObjects.objects.color,
//                            20f,
//                            point
//                        )
//                    for (path in pathsAndObjects.roomsPath.paths)
//                        drawScope.drawPath(path, pathsAndObjects.roomsPath.color, style = Stroke(10f))
//
//                    for (path in pathsAndObjects.elevatorsPath.paths)
//                        drawScope.drawPath(path, pathsAndObjects.elevatorsPath.color, style = Stroke(10f))
//
//                    for (path in pathsAndObjects.outerWallPath.paths)
//                        drawScope.drawPath(path, pathsAndObjects.outerWallPath.color, style = Stroke(10f))
//
//                    for (path in pathsAndObjects.internalWallsPath.paths)
//                        drawScope.drawPath(path, pathsAndObjects.internalWallsPath.color, style = Stroke(10f))
//
//                    for (path in pathsAndObjects.othersPath.paths)
//                        drawScope.drawPath(path, pathsAndObjects.othersPath.color, style = Stroke(10f))
//
//                    for (path in pathsAndObjects.stairsPath.paths)
//                        drawScope.drawPath(path, pathsAndObjects.stairsPath.color, style = Stroke(10f))
//                }
//            }
//        }
//
//        Canvas(
//            modifier = modifier
//                .aspectRatio(3 / 2f)
//                .fillMaxSize()
//        ) {
//            drawContent(this)
//        }
//    } else {
//        Text(text = "Загрузка...")
//    }
//}







fun initMap(
    pointsList: List<Point>,
    pathsList: List<PathModel>,
    width: Float,
    height: Float
): PathsAndObjectsHolderForDrawing{
    val paths = PathsAndObjectsHolderForDrawing()
    val mutablePointsList = pointsList.toMutableSet()

    for (pathModel in pathsList) {
        val pointsForPath = mutableListOf<Point>()

        mutablePointsList.forEach { point ->
            if (isPointObject(point)) {
                paths.objects.objects.add(Offset(point.x * width, point.y * height))

            }
            else if (pathModel.pathId == point.pathId){
                pointsForPath.add(point)
            }
        }
        pointsForPath.sortBy { it.inPathId }
        val path = makePathFromPoints(pointsForPath, width, height)
        when (pathModel.pathType){
            PathType.ELEVATOR -> {paths.elevatorsPath.paths.add(path)}
            PathType.STAIRS -> {paths.stairsPath.paths.add(path)}
            PathType.ROOM -> {paths.roomsPath.paths.add(path)


            }
            PathType.OUTER_WALL -> {paths.outerWallPath.paths.add(path)}
            PathType.INTERNAL_WALL -> {paths.internalWallsPath.paths.add(path)}
            PathType.OTHER -> {paths.othersPath.paths.add(path)}
        }
    }
    return paths
}


fun makePathFromPoints(points: List<Point>, width: Float, height: Float): Path {
    val path = Path()
    for ((index, point) in points.withIndex()){
        if (index == 0) path.moveTo(point.x * width, point.y * height)
        else {
            when (point.pointParameter) {
                PointParameter.LINE -> path.lineTo(point.x * width, point.y * height)
                PointParameter.BEZIER3 -> cubicBezierForThreePoints(listOf(points[index - 2], points[index - 1], points[index]), path, width, height)
                else -> Unit
            }
        }
    }
    path.close()
    return path
}

fun cubicBezierForThreePoints(points: List<Point>, path: Path, width: Float, height: Float){
    val secondPoint = Pair(points[0].x * width, ((points[1].y - points[0].y) * 4 / 3 + points[0].y) * height)
    val thirdPoint = Pair(points[2].x * width, ((points[1].y - points[2].y) * 4 / 3 + points[2].y) * height)
    val fourthPoint = Pair(points[2].x * width, points[2].y * height)
    path.cubicTo(secondPoint.first, secondPoint.second, thirdPoint.first, thirdPoint.second, fourthPoint.first, fourthPoint.second)
}

fun isPointObject(point: Point): Boolean = point.pointType == PointType.OBJECT

//        coroutineScope.launch {
//            floorState.collect {State ->
//                initMap(floorState.value.points, floorState.value.paths, width, height)
//            }
//        }
//
//            pathsAndObjects = initMap(floorState.value.points, floorState.value.paths, width, height)
//            if (pathsAndObjects.elevatorsPath.paths.isNotEmpty()) {
//                for (item in pathsAndObjects.elevatorsPath.paths) {
//                    drawPath(item, Color.Green, style = Stroke(2.dp.toPx()))
//                }
//            }
//
//            if (pathsAndObjects.stairsPath.paths.isNotEmpty()) {
//                for (item in pathsAndObjects.stairsPath.paths) {
//                    drawPath(item, Color.Green, style = Stroke(2.dp.toPx()))
//                }
//            }
//
//
//            if (pathsAndObjects.roomsPath.paths.isNotEmpty()) {
//                for (item in pathsAndObjects.roomsPath.paths) {
//                    drawPath(item, Color.Green, style = Stroke(2.dp.toPx()))
//                }
//            }
//
//            if (pathsAndObjects.outerWallPath.paths.isNotEmpty()) {
//                for (item in pathsAndObjects.outerWallPath.paths) {
//                    drawPath(item, Color.Green, style = Stroke(2.dp.toPx()))
//                }
//            }
//
//            if (pathsAndObjects.internalWallsPath.paths.isNotEmpty()) {
//                for (item in pathsAndObjects.internalWallsPath.paths) {
//                    drawPath(item, Color.Green, style = Stroke(2.dp.toPx()))
//                }
//            }
//
//            if (pathsAndObjects.othersPath.paths.isNotEmpty()) {
//                for (item in pathsAndObjects.othersPath.paths) {
//                    drawPath(item, Color.Green, style = Stroke(2.dp.toPx()))
//                }
//            }
//
//            if (pathsAndObjects.objects.objects.isNotEmpty()) {
//                Log.d("pointShouldBeDraw", "whyNot?")
//                drawPoints(
//                    pathsAndObjects.objects.objects,
//                    pointMode = PointMode.Points,
//                    color = Color.Red,
//                    strokeWidth = 10f
//                )
//            }