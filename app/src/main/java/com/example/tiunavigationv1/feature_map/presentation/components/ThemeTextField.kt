package com.example.tiunavigationv1.feature_map.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiunavigationv1.ui.theme.light_gray

@Composable
fun ThemeTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,


    ) {
        TextField(
            colors = TextFieldDefaults
                .textFieldColors(
                textColor=MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.surface
                ),
            placeholder = {Text(text = hint)},
            trailingIcon = {Icon(Icons.Default.Search, contentDescription = null)},
            shape = MaterialTheme.shapes.medium,
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .background(light_gray)
        )
    }
}

