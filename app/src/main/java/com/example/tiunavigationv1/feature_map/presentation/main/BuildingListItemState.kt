package com.example.tiunavigationv1.feature_map.presentation.main

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.MutableState
import com.example.tiunavigationv1.feature_map.domain.model.Building
import com.example.tiunavigationv1.ui.theme.light_gray
import com.example.tiunavigationv1.ui.theme.red


data class BuildingListItemState(
    var building: Building,
    var color: MutableState<Color>,
) {
    init {
        color.value = if (building.isFavorite) red else light_gray
    }
}



