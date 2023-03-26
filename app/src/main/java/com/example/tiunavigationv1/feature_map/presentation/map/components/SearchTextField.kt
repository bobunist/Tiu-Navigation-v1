package com.example.tiunavigationv1.feature_map.presentation.map.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tiunavigationv1.feature_map.presentation.main.components.ThemeTextField

@Composable
fun SearchTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    ThemeTextField(
        text = text,
        hint = hint,
        modifier = modifier,
        onValueChange = onValueChange
    )
}