package com.example.tiunavigationv1.feature_map.presentation.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tiunavigationv1.R
import com.example.tiunavigationv1.feature_map.presentation.main.components.BuildingItem
import com.example.tiunavigationv1.feature_map.presentation.main.components.ThemeTextField
import com.example.tiunavigationv1.feature_map.presentation.main.components.TopBar
import com.example.tiunavigationv1.feature_map.presentation.util.Screen



@ExperimentalAnimationApi
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel()
){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val favoriteList = viewModel.favoriteList
    val searchList = viewModel.searchList
    val scaffoldState = rememberScaffoldState()
    val address = viewModel.searchTextFieldState.value

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar() }
    ) {padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ThemeTextField(
                        modifier = Modifier
                            .padding(10.dp, 0.dp)
                            .heightIn(min = 30.dp)
                        ,
                        text = address.text,
                        hint = address.hint,
                        onValueChange = {
                            viewModel.onEvent(MainScreenEvent.EnteredAddress(it))
                        },
                    )
                    if (address.isListVisible.value) {
                        
                            Column(
                                modifier = Modifier,
                            ){
                                repeat(searchList.size){index ->
                                    val item = searchList[index]
                                    BuildingItem(
                                        modifier = Modifier
                                            .fillMaxWidth()

                                        ,
                                        text = item.building.buildingAddress,
                                        onClick = {
                                            navController.navigate(Screen.MapScreen.route +
                                                    "?buildingId=${item.building.buildingId}")
                                        },
                                        onFavoriteIconClick = {
                                            viewModel.onEvent(MainScreenEvent.ReverseIsFavoriteField(item.building.buildingId!!))
                                        },
                                        color = item.color.value
                                    )
                                    Spacer(modifier = Modifier.size(2.dp))
                                }
                            }

                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
                Text(text = stringResource(id = R.string.favorite))
                Spacer(modifier = Modifier.size(20.dp))
                Column(modifier = Modifier) {

                    repeat(favoriteList.size){index ->
                        val item = favoriteList[index]
                        BuildingItem(
                            text = item.building.buildingAddress,
                            onClick = {
                                navController.navigate(Screen.MapScreen.route +
                                        "?buildingId=${item.building.buildingId}")
                            },
                            onFavoriteIconClick = {
                                viewModel.onEvent(MainScreenEvent.ReverseIsFavoriteField(item.building.buildingId!!))
                            },
                            color = item.color.value,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.size(2.dp))
                    }
                }
            }
        }
    }
}