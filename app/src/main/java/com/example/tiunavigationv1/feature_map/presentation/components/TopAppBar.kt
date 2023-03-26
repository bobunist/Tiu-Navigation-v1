package com.example.tiunavigationv1.feature_map.presentation.main.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
){
    Surface(modifier = Modifier
        .height(50.dp)
        .fillMaxWidth(),
        color = MaterialTheme.colors.secondary,) {
        Row(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "ТИУ НАВИГАЦИЯ",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSecondary)
        }
    }
}