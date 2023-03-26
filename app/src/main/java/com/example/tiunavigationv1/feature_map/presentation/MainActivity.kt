package com.example.tiunavigationv1.feature_map.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.example.tiunavigationv1.ui.theme.TiuNavigationV1Theme
// AndroidX
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tiunavigationv1.feature_map.presentation.main.MainScreen
import com.example.tiunavigationv1.feature_map.presentation.map.MapScreen
import com.example.tiunavigationv1.feature_map.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

// Room

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TiuNavigationV1Theme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.MainScreen.route
                ) {
                    composable(route = Screen.MainScreen.route) {
                        MainScreen(navController = navController)
                    }
                    composable(route = Screen.MapScreen.route +
                            "?buildingId={buildingId}",
                    arguments = listOf(
                        navArgument(
                        name = "buildingId"
                    ){
                        type = NavType.LongType
                        defaultValue = -1L
                    })
                    ){
                        MapScreen(navController = navController)
                    }
                }
            }
        }
    }
}

