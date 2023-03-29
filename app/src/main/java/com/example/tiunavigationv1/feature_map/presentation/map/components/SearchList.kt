package com.example.tiunavigationv1.feature_map.presentation.map.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tiunavigationv1.feature_map.domain.model.Point
import com.example.tiunavigationv1.feature_map.presentation.map.MapElement

@Composable
fun SearchList(searchList: List<MapElement>, onItemClick: (MapElement) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(searchList) { item ->
            PointItem(
                text = item.getName(),
                onClick = { onItemClick(item) }
            )
        }
    }
}