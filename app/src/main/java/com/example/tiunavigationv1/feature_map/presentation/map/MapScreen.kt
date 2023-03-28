package com.example.tiunavigationv1.feature_map.presentation.map

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tiunavigationv1.feature_map.domain.model.Point
import com.example.tiunavigationv1.feature_map.presentation.main.components.ThemeTextField
import com.example.tiunavigationv1.feature_map.presentation.main.components.TopBar
import com.example.tiunavigationv1.feature_map.presentation.map.components.*
import kotlinx.coroutines.flow.StateFlow

@ExperimentalAnimationApi
@Composable
fun MapScreen(
    navController: NavController,
    viewModel: MapScreenViewModel = hiltViewModel()
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthDp = configuration.screenWidthDp
    val screenHeightDp = configuration.screenHeightDp

    val screenWidthPx = with(density) { screenWidthDp.dp.toPx() }

    val screenHeightPx = with(density) { screenHeightDp.dp.toPx() }

    val scaffoldState = rememberScaffoldState()

    val floorsList = viewModel.floorsList

    val floorState = viewModel.floorState.collectAsState()

    val startPointState = viewModel.floorState.value.startPoint
    val endPointState = viewModel.floorState.value.endPoint

    val isSearchListVisible = viewModel.searchListState.isSearchListVisible.value

    val searchList = viewModel.searchListState.searchList


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar() },
    ) { padding ->
        Box(Modifier.fillMaxSize()) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.Center)) {
                BoxWithConstraints {
                    Map3(floorState = floorState, configuration =  configuration, density = density)
                }
            }
            FloorBar(
                Modifier
                    .height(screenHeightDp.dp / 2)
                    .width(60.dp)
                    .padding(start = 5.dp)
                    .align(Alignment.CenterStart),
                floorsList,
                onClick = { floorId -> viewModel.onEvent(MapScreenEvent.OnChangeFloor(floorId)) },
                currentFloor = floorState.value.currentFloor
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
            }
            Column(modifier = Modifier
                .padding(0.dp, 10.dp)
                .fillMaxSize(),
            ) {
                SearchTextField(
                    text = startPointState.text.value,
                    hint = startPointState.hint,
                    modifier = Modifier
                        .padding(10.dp, 0.dp)
                        .heightIn(min = 30.dp),
                    onValueChange = {viewModel.onEvent(MapScreenEvent.EnteredStartPoint(it))}
                )
                Spacer(modifier = Modifier.size(5.dp))
                SearchTextField(
                    text = endPointState.text.value,
                    hint = endPointState.hint,
                    modifier = Modifier
                        .padding(10.dp, 0.dp)
                        .heightIn(min = 30.dp),
                    onValueChange = {viewModel.onEvent(MapScreenEvent.EnteredEndPoint(it))})
                if (isSearchListVisible) {
                    SearchList(searchList, onItemClick = { item ->
                        viewModel.onEvent(MapScreenEvent.SetPoint(item))
                    })
                }
            }
        }
    }
}






