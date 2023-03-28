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
import com.example.tiunavigationv1.feature_map.domain.model.Path as PathModel
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.input.pointer.*
import kotlin.math.*

@Composable
fun Map3(
    modifier: Modifier = Modifier,
    floorState: State<FloorState>,
    configuration: Configuration,
    density: Density,
    onTapEvent: (Point?, PathModel?) -> Unit
) {
    val width = with(density) { configuration.screenWidthDp.dp.toPx() }
    val height = width / 3 * 2
    val (isDataLoaded, setDataLoaded) = remember { mutableStateOf(false) }
    var pathsAndObjects =  PathsAndObjectsHolderForDrawing()
    LaunchedEffect(floorState.value.paths) {

        setDataLoaded(false)
        if (floorState.value.points.isNotEmpty() && floorState.value.paths.isNotEmpty()) {
            setDataLoaded(true)
        }
    }

    if (isDataLoaded) {
        val drawContent by remember(floorState.value) {
            derivedStateOf {
                pathsAndObjects = initMap(floorState.value.points, floorState.value.paths, width, height);
                { drawScope: DrawScope ->
                    drawScope.drawPoints(pathsAndObjects.objects.objects,
                        PointMode.Points, pathsAndObjects.objects.color, 30f)

                    for (point in pathsAndObjects.objects.objects)
                        drawScope.drawCircle(
                            pathsAndObjects.objects.color,
                            20f,
                            point
                        )
                    for (path in pathsAndObjects.roomsPath.paths)
                        drawScope.drawPath(path, pathsAndObjects.roomsPath.color, style = Stroke(10f))

                    for (path in pathsAndObjects.elevatorsPath.paths)
                        drawScope.drawPath(path, pathsAndObjects.elevatorsPath.color, style = Stroke(10f))

                    for (path in pathsAndObjects.outerWallPath.paths)
                        drawScope.drawPath(path, pathsAndObjects.outerWallPath.color, style = Stroke(10f))

                    for (path in pathsAndObjects.internalWallsPath.paths)
                        drawScope.drawPath(path, pathsAndObjects.internalWallsPath.color, style = Stroke(10f))

                    for (path in pathsAndObjects.othersPath.paths)
                        drawScope.drawPath(path, pathsAndObjects.othersPath.color, style = Stroke(10f))

                    for (path in pathsAndObjects.stairsPath.paths)
                        drawScope.drawPath(path, pathsAndObjects.stairsPath.color, style = Stroke(10f))
                }
            }
        }

        Canvas(
            modifier = modifier
                .aspectRatio(3 / 2f)
                .fillMaxSize()
                .pointerInput(Unit){
                    detectTapGestures { tap: Offset ->
                        for (circleCenter in pathsAndObjects.objects.objects){
                            if (isPointInsideCircle(tap, circleCenter, 25f)){
                                val pointModel = pathsAndObjects.pointsMap[circleCenter]
                                onTapEvent(pointModel, null)
                                return@detectTapGestures
                            }
                        }
                        for (path in pathsAndObjects.roomsPath.paths){
                            if (isPointInsidePolygon())
                            val pathModel = pathsAndObjects.pathsMap

                        }
                        for (path in pathsAndObjects.elevatorsPath.paths){

                        }
                        for (path in pathsAndObjects.stairsPath.paths){

                        }
                    }
                }
        ) {
            drawContent(this)
        }
    } else {
        Text(text = "Загрузка...")
    }
}

private fun isPointInsideCircle(point: Offset, circleCenter: Offset, radius: Float): Boolean {
    val distance = sqrt((point.x - circleCenter.x).pow(2) + (point.y - circleCenter.y).pow(2))
    return distance <= radius
}

private fun isPointInsidePolygon(polygonPoints: List<Offset>, point: Offset): Boolean {
    var crossings = 0

    for (i in polygonPoints.indices) {
        val p1 = polygonPoints[i]
        val p2 = polygonPoints[(i + 1) % polygonPoints.size] // Wrap around to the first vertex for the last edge

        if (isRayCrossingEdge(point, p1, p2)) {
            crossings++
        }
    }

    return crossings % 2 != 0
}

fun isRayCrossingEdge(p: Offset, a: Offset, b: Offset): Boolean {
    if (a.y > b.y) {
        return isRayCrossingEdge(p, b, a)
    }
    if (p.y == a.y || p.y == b.y) {
        return false
    }
    if (p.y > b.y || p.y < a.y) {
        return false
    }
    if (p.x >= max(a.x, b.x)) {
        return false
    }

    if (p.x < min(a.x, b.x)) {
        return true
    }

    val edgeOrientation = (b.y - a.y) / (b.x - a.x)
    val testOrientation = (p.y - a.y) / (p.x - a.x)

    return testOrientation >= edgeOrientation
}





fun initMap(
    pointsList: List<Point>,
    pathsList: List<PathModel>,
    width: Float,
    height: Float
): PathsAndObjectsHolderForDrawing{
    val paths = PathsAndObjectsHolderForDrawing()
    val mutablePointsList = pointsList.toMutableList()
    val pathsMap: MutableMap<Path, Pair<PathModel, List<>>> = mutableMapOf()
    val pointsMap: MutableMap<Offset, Point> = mutableMapOf()

    for (pathModel in pathsList) {
        val pointsForPath = mutableListOf<Point>()
        mutablePointsList.forEach { point ->
            if (isPointObject(point) && !mutablePointsList.contains(point)) {
                val offsetPoint = Offset(point.x * width, point.y * height)
                paths.objects.objects.add(offsetPoint)
                pointsMap[offsetPoint] = point
            }
            else if (pathModel.pathId == point.pathId){
                pointsForPath.add(point)
            }
        }
        pointsForPath.sortBy { it.inPathId }
        val path = makePathFromPoints(pointsForPath, width, height)
        pathsMap[path] = pathModel
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
    paths.pointsMap.putAll(pointsMap)
    paths.pathsMap.putAll(pathsMap)
    return paths
}

fun pointToOffset(point: Point, width: Float, height: Float): Offset{
    return Offset(point.x * width, point.y * height)
}

fun makePathFromPoints(points: List<Point>, width: Float, height: Float): Path {
    val path = Path()
    for ((index, point) in points.withIndex()){
        val pointOffset = pointToOffset(point, width, height)
        if (index == 0) path.moveTo(pointOffset.x, pointOffset.y)
        else {
            when (point.pointParameter) {
                PointParameter.LINE -> path.lineTo(pointOffset.x, pointOffset.y)
                PointParameter.BEZIER3 -> cubicBezierForThreePoints(listOf(points[index - 2], points[index - 1], points[index]), path, width, height)
                else -> Unit
            }
        }
    }
    path.close()
    return path
}

fun cubicBezierForThreePoints(points: List<Offset>, path: Path){
    val secondPoint = Offset(points[0].x, ((points[1].y - points[0].y) * 4 / 3 + points[0].y))
    val thirdPoint = Offset(points[2].x, ((points[1].y - points[2].y) * 4 / 3 + points[2].y))
    val fourthPoint = Offset(points[2].x, points[2].y)
    path.cubicTo(secondPoint.x, secondPoint.y, thirdPoint.x, thirdPoint.y, fourthPoint.x, fourthPoint.y)
}

fun isPointObject(point: Point): Boolean = point.pointType == PointType.OBJECT
