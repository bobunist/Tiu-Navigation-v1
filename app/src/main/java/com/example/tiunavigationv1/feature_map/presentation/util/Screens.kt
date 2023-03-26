package com.example.tiunavigationv1.feature_map.presentation.util

sealed class Screen (val route: String){
    object MainScreen: Screen("main_screen")
    object MapScreen: Screen("map_screen")
    object MapRedactorScreen: Screen("map_redactor_screen")
}
