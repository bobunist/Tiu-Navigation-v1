package com.example.tiunavigationv1.feature_map.presentation.map.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import kotlinx.coroutines.flow.StateFlow
import com.example.tiunavigationv1.feature_map.domain.model.Path as PathModel

//@Composable
//fun Map(modifier: Modifier = Modifier,
//        floorState: StateFlow<FloorState>,
//        configuration: Configuration,
//        density: Density
//) {
//
//
//    val floorState = floorState.collectAsState()
//    var pathsAndObjects: MutableState<PathsAndObjectsHolderForDrawing> = remember { mutableStateOf(PathsAndObjectsHolderForDrawing()) }
//
//
//    LaunchedEffect(floorState.value) {
//        val floor = floorState.value
//        pathsAndObjects.value = withContext(Dispatchers.Default) {
//            initMap(floor.points, floor.paths,
//                configuration.screenWidthDp.toFloat() * density.density,
//                configuration.screenWidthDp.toFloat() * density.density
//            )
//        }
//    }
//
//    val paint = Paint()
//    paint.color = Color.Red
//    paint.strokeWidth = 10f
//    val block: MutableState<Canvas.() -> Unit> =
//        remember { mutableStateOf( {drawPoints(PointMode.Points, listOf(Offset(450f, 450f)), paint) } ) }
//
//    Canvas(
//        modifier = modifier
//            .aspectRatio(3 / 2f)
//            .fillMaxSize()
//    ) {
//        val height = size.height
//        val width = size.width
//
//        floorState.value.let {
//            drawPoints(pathsAndObjects.value.objects.objects, PointMode.Points, Color.Red, 10f)
//        }
//    }
//}
//
//@Composable
//fun Map1(
//    modifier: Modifier = Modifier,
//    floorState: StateFlow<FloorState>,
//    configuration: Configuration,
//    density: Density
//) {
//    val paint = remember { Paint().apply {
//        color = Color.Red
//        strokeWidth = 10f
//    } }
//
//    val pathsAndObjects by remember { mutableStateOf(PathsAndObjectsHolderForDrawing()) }
//
//    LaunchedEffect(floorState.value) {
//        val width = configuration.screenWidthDp.toFloat() * density.density
//        val height = configuration.screenWidthDp.toFloat() * density.density
//
//        val newPathsAndObjects = withContext(Dispatchers.Default) {
//            initMap(floorState.value.points, floorState.value.paths, width, height)
//        }
//
//        pathsAndObjects.elevatorsPath.paths = newPathsAndObjects.elevatorsPath.paths
//        pathsAndObjects.stairsPath.paths = newPathsAndObjects.stairsPath.paths
//        pathsAndObjects.roomsPath.paths = newPathsAndObjects.roomsPath.paths
//        pathsAndObjects.outerWallPath.paths = newPathsAndObjects.outerWallPath.paths
//        pathsAndObjects.internalWallsPath.paths = newPathsAndObjects.internalWallsPath.paths
//        pathsAndObjects.othersPath.paths = newPathsAndObjects.othersPath.paths
//        pathsAndObjects.objects.objects = newPathsAndObjects.objects.objects
//    }
//
//    Canvas(
//        modifier = modifier
//            .aspectRatio(3 / 2f)
//            .fillMaxSize()
//    ) {
//        pathsAndObjects.elevatorsPath.paths.let {
//            for (path in it) drawPath(path, Color.Blue, paint.strokeWidth)
//        }
//
//        pathsAndObjects.stairsPath.paths.let {
//            for (path in it) drawPath(path, Color.Green, paint.strokeWidth)
//        }
//
//        pathsAndObjects.roomsPath.paths.let {
//            for (path in it) drawPath(path, Color.Yellow, paint.strokeWidth)
//        }
//
//        pathsAndObjects.outerWallPath.paths.let {
//            for (path in it) drawPath(path, Color.Gray, paint.strokeWidth)
//        }
//
//        pathsAndObjects.internalWallsPath.paths.let {
//            for (path in it) drawPath(path, Color.LightGray, paint.strokeWidth)
//        }
//
//        pathsAndObjects.othersPath.paths.let {
//            for (path in it) drawPath(path, Color.Cyan, paint.strokeWidth)
//        }
//        pathsAndObjects.objects.objects.let {
//            drawPoints(pathsAndObjects.objects.objects, PointMode.Points, Color.Red, 10f)
//        }
//
//    }
//}

@Composable
fun Map3(
    modifier: Modifier = Modifier,
    floorState: State<FloorState>,
    configuration: Configuration,
    density: Density
) {
    val width = with(density) { configuration.screenWidthDp.dp.toPx() }
    val height = width / 3 * 2
    val (isDataLoaded, setDataLoaded) = remember { mutableStateOf(false) }

    LaunchedEffect(floorState.value) {
        delay(50)
        setDataLoaded(false)
        if (floorState.value.points.isNotEmpty() && floorState.value.paths.isNotEmpty()) {
            setDataLoaded(true)
        }
    }

    if (isDataLoaded) {
        val drawContent by remember(floorState.value) {
            derivedStateOf {
                val pathsAndObjects = initMap(floorState.value.points, floorState.value.paths, width, height);
                { drawScope: DrawScope ->
                    drawScope.drawPoints(pathsAndObjects.objects.objects, PointMode.Points, Color.Red, 10f)

                    for (path in pathsAndObjects.roomsPath.paths) {
                        drawScope.drawPath(path, Color.Green, style = Stroke(10f))
                    }
                }
            }
        }

        Canvas(
            modifier = modifier
                .aspectRatio(3 / 2f)
                .fillMaxSize()
        ) {
            drawContent(this)
        }
    } else {
        Text(text = "Загрузка...")
    }
}

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
    Log.d("Init", paths.objects.objects.size.toString())
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