package com.example.tiunavigationv1.feature_map.presentation.map.components

import android.content.res.Configuration
import androidx.compose.animation.core.*
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
import com.example.tiunavigationv1.feature_map.domain.model.Node
import com.example.tiunavigationv1.feature_map.presentation.map.MapElement
import kotlinx.coroutines.delay
import kotlin.math.*


@Composable
fun Map3(
    modifier: Modifier = Modifier,
    floorState: State<FloorState>,
    configuration: Configuration,
    density: Density,
    onTapEvent: (MapElement) -> Unit
) {
    val width = with(density) { configuration.screenWidthDp.dp.toPx() }
    val height = width * 1
    val (isDataLoaded, setDataLoaded) = remember { mutableStateOf(false) }
    var pathsAndObjects =  PathsAndObjectsHolderForDrawing()

    val drawPercentage = remember { Animatable(0f) }

    LaunchedEffect(floorState.value.way) {
        drawPercentage.snapTo(0f)
        drawPercentage.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        )
    }


    LaunchedEffect(floorState.value) {
        delay(50)
//        может работает и без delay
        setDataLoaded(false)
        if (floorState.value.points.isNotEmpty() || floorState.value.paths.isNotEmpty()) {
            setDataLoaded(true)
            drawPercentage.snapTo(0f)
            drawPercentage.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
        }
    }

    if (isDataLoaded) {
        val drawContent by remember(floorState.value) {
            derivedStateOf {
                pathsAndObjects = initMap(
                    floorState.value.points,
                    floorState.value.paths,
                    width,
                    height
                );
                { drawScope: DrawScope ->
                    drawScope.drawMapElements(
                        pathsAndObjects = pathsAndObjects,
                        floorState = floorState,
                        width = width,
                        height = height,
                        drawPercentage = drawPercentage
                    )



                }
            }
        }
        Canvas(
            modifier = modifier
                .aspectRatio(1f)
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { tap: Offset ->
                        for (circleCenter in pathsAndObjects.objects.objects) {
                            if (isPointInsideCircle(tap, circleCenter, 25f)) {
                                val pointModel = pathsAndObjects.pointsMap[circleCenter]
                                pointModel?.let { onTapEvent(MapElement.PointElement(it)) }
                                return@detectTapGestures
                            }
                        }
                        for (path in pathsAndObjects.roomsPath.paths) {
                            val polygonPoints = pathsAndObjects.pathsMap[path]?.second
                            val pathModel: com.example.tiunavigationv1.feature_map.domain.model.Path =
                                pathsAndObjects.pathsMap[path]!!.first
                            if (polygonPoints?.let { isPointInsidePolygon(it, tap) } == true) {
                                onTapEvent(MapElement.PathElement(pathModel))
                                return@detectTapGestures
                            }
                        }
                        for (path in pathsAndObjects.elevatorsPath.paths) {
                            val polygonPoints = pathsAndObjects.pathsMap[path]?.second
                            val pathModel: com.example.tiunavigationv1.feature_map.domain.model.Path =
                                pathsAndObjects.pathsMap[path]!!.first
                            if (polygonPoints?.let { isPointInsidePolygon(it, tap) } == true) {
                                onTapEvent(MapElement.PathElement(pathModel))
                                return@detectTapGestures
                            }
                        }
                        for (path in pathsAndObjects.stairsPath.paths) {
                            val polygonPoints = pathsAndObjects.pathsMap[path]?.second
                            val pathModel: com.example.tiunavigationv1.feature_map.domain.model.Path =
                                pathsAndObjects.pathsMap[path]!!.first
                            if (polygonPoints?.let { isPointInsidePolygon(it, tap) } == true) {
                                onTapEvent(MapElement.PathElement(pathModel))
                                return@detectTapGestures
                            }
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


fun DrawScope.drawMapElements(
    pathsAndObjects: PathsAndObjectsHolderForDrawing,
    floorState: State<FloorState>,
    width: Float,
    height: Float,
    drawPercentage: Animatable<Float, AnimationVector1D>
) {
    for (point in pathsAndObjects.objects.objects)
        drawCircle(
            pathsAndObjects.objects.color,
            20f,
            point
        )
    for (path in pathsAndObjects.roomsPath.paths)
        drawPath(path, pathsAndObjects.roomsPath.color, style = Stroke(10f))

    for (path in pathsAndObjects.elevatorsPath.paths)
        drawPath(path, pathsAndObjects.elevatorsPath.color, style = Stroke(10f))

    for (path in pathsAndObjects.outerWallPath.paths)
        drawPath(path, pathsAndObjects.outerWallPath.color, style = Stroke(10f))

    for (path in pathsAndObjects.internalWallsPath.paths)
        drawPath(path, pathsAndObjects.internalWallsPath.color, style = Stroke(10f))

    for (path in pathsAndObjects.othersPath.paths)
        drawPath(path, pathsAndObjects.othersPath.color, style = Stroke(10f))

    for (path in pathsAndObjects.stairsPath.paths)
        drawPath(path, pathsAndObjects.stairsPath.color, style = Stroke(10f))

    floorState.value.startObject.obj.value?.let { startObject ->
        when (startObject) {
            is MapElement.PointElement -> {
                val point = pointToOffset(startObject.point, width, height)
                drawCircle(Color.Green, 30f, point)
            }
            is MapElement.PathElement -> {
                val path = pathsAndObjects.pathsMap.filterValues { it.first == startObject.path }.keys.first()
                drawPath(path, Color.Green, style = Stroke(20f))
            }
        }
    }

    floorState.value.endObject.obj.value?.let { endObject ->
        when (endObject) {
            is MapElement.PointElement -> {
                val offset = pointToOffset(endObject.point, width, height)
                drawCircle(Color.Red, 30f, offset)
            }
            is MapElement.PathElement -> {
                val path = pathsAndObjects.pathsMap.filterValues { it.first == endObject.path }.keys.first()
                drawPath(path, Color.Red, style = Stroke(20f))
            }
        }
    }
    floorState.value.way.let { way ->
        val points = makeListOfNodes(way, width, height)
        val newPath = Path()

        if (points.isNotEmpty()) {
            val percentage = drawPercentage.value
            val lastIndex = (points.size - 1) * percentage
            val currentIndex = lastIndex.toInt()
            val remainder = lastIndex - currentIndex

            newPath.moveTo(points[0].x, points[0].y)

            for (i in 1..currentIndex) {
                newPath.lineTo(points[i].x, points[i].y)
            }

            if (currentIndex < points.size - 1) {
                val startX = points[currentIndex].x
                val startY = points[currentIndex].y
                val endX = points[currentIndex + 1].x
                val endY = points[currentIndex + 1].y

                newPath.lineTo(
                    startX + (endX - startX) * remainder,
                    startY + (endY - startY) * remainder
                )
            }

            drawPath(newPath, Color.Red, style = Stroke(10f))
        }
    }
}

fun makeListOfNodes(way: List<Node>, width: Float, height: Float): List<Offset> {
    return way.map { node ->
        nodeToOffset(node, width, height)
    }
}


private fun isPointInsideCircle(point: Offset, circleCenter: Offset, radius: Float): Boolean {
    val distance = sqrt((point.x - circleCenter.x).pow(2) + (point.y - circleCenter.y).pow(2))
    return distance <= radius
}

private fun isPointInsidePolygon(polygonPoints: List<Offset>, point: Offset): Boolean {
    val vertices = polygonPoints.map { Vertex(it.x, it.y) }
    val pointVertex = Vertex(point.x, point.y)

    var inside = false
    var i = 0
    var j = vertices.size - 1
    while (i < vertices.size) {
        val vertexI = vertices[i]
        val vertexJ = vertices[j]

        if (vertexI.y < pointVertex.y && vertexJ.y >= pointVertex.y || vertexJ.y < pointVertex.y && vertexI.y >= pointVertex.y) {
            if (vertexI.x + (pointVertex.y - vertexI.y) / (vertexJ.y - vertexI.y) * (vertexJ.x - vertexI.x) < pointVertex.x) {
                inside = !inside
            }
        }
        j = i++
    }
    return inside
}

private data class Vertex(val x: Float, val y: Float)

private fun initMap(
    pointsList: List<Point>,
    pathsList: List<PathModel>,
    width: Float,
    height: Float
): PathsAndObjectsHolderForDrawing {
    val paths = PathsAndObjectsHolderForDrawing()
    val mutablePointsList = pointsList.toMutableSet()
    val pathsMap: MutableMap<Path, Pair<PathModel, List<Offset>>> = mutableMapOf()
    val pointsMap: MutableMap<Offset, Point> = mutableMapOf()

    if (pathsList.isEmpty()) {
        pointsList.filter(::isPointObject).forEach { point ->
            val offsetPoint = Offset(point.x * width, point.y * height)
            paths.objects.objects.add(offsetPoint)
            pointsMap[offsetPoint] = point
        }
    } else {
        for (pathModel in pathsList) {
            val pointsForPath = mutableListOf<Point>()
            mutablePointsList.forEach { point ->
                if (isPointObject(point)) {
                    val offsetPoint = Offset(point.x * width, point.y * height)
                    paths.objects.objects.add(offsetPoint)
                    pointsMap[offsetPoint] = point
                } else if (pathModel.pathId == point.pathId) {
                    pointsForPath.add(point)
                }
            }
            pointsForPath.sortBy { it.inPathId }
            val path = makePathFromPoints(pointsForPath, width, height)
            pathsMap[path] = Pair(pathModel, pointsForPath.map { point -> pointToOffset(point, width, height) })
            when (pathModel.pathType) {
                PathType.ELEVATOR -> {
                    paths.elevatorsPath.paths.add(path)
                }
                PathType.STAIRS -> {
                    paths.stairsPath.paths.add(path)
                }
                PathType.ROOM -> {
                    paths.roomsPath.paths.add(path)
                }
                PathType.OUTER_WALL -> {
                    paths.outerWallPath.paths.add(path)
                }
                PathType.INTERNAL_WALL -> {
                    paths.internalWallsPath.paths.add(path)
                }
                PathType.OTHER -> {
                    paths.othersPath.paths.add(path)
                }
            }
        }
    }

    paths.pointsMap.putAll(pointsMap)
    paths.pathsMap.putAll(pathsMap)

    return paths
}


private fun pointToOffset(point: Point, width: Float, height: Float): Offset{
    return Offset(point.x * width, point.y * height)
}

private fun nodeToOffset(node: Node, width: Float, height: Float): Offset{
    return Offset(node.x * width, node.y * height)
}


private fun makePathFromPoints(points: List<Point>, width: Float, height: Float): Path {
    val path = Path()
    for ((index, point) in points.withIndex()){
        val pointOffset = pointToOffset(point, width, height)
        if (index == 0) path.moveTo(pointOffset.x, pointOffset.y)
        else {
            when (point.pointParameter) {
                PointParameter.LINE -> path.lineTo(pointOffset.x, pointOffset.y)
                PointParameter.BEZIER3 -> {
                    val point1 = pointToOffset(points[index - 2], width, height)
                    val point2 = pointToOffset(points[index - 1], width, height)
                    val point3 = pointToOffset(points[index], width, height)
                    cubicBezierForThreePoints(listOf(point1, point2, point3), path)
                }
                else -> Unit
            }
        }
    }
    path.close()
    return path
}

private fun cubicBezierForThreePoints(points: List<Offset>, path: Path){
    val secondPoint = Offset(points[0].x, ((points[1].y - points[0].y) * 4 / 3 + points[0].y))
    val thirdPoint = Offset(points[2].x, ((points[1].y - points[2].y) * 4 / 3 + points[2].y))
    val fourthPoint = Offset(points[2].x, points[2].y)
    path.cubicTo(secondPoint.x, secondPoint.y, thirdPoint.x, thirdPoint.y, fourthPoint.x, fourthPoint.y)
}

private fun isPointObject(point: Point): Boolean = point.pointType == PointType.OBJECT
