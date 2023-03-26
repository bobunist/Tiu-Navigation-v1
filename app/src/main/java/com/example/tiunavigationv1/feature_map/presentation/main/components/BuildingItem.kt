package com.example.tiunavigationv1.feature_map.presentation.main.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BuildingItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
    color: Color
){
    Surface(modifier = Modifier,
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = modifier.fillMaxSize()
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(modifier = modifier
                    .clickable(onClick = onClick)
                    .padding(20.dp, 5.dp)
                    .fillMaxWidth()
                    .onFocusChanged { }
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Text(text = text)
                    IconButton(onClick = onFavoriteIconClick) {
                        Icon(imageVector = Icons.Default.Favorite,
                            contentDescription = "is favorite field",
                            tint = color)
                    }
                }
//            Spacer(modifier = Modifier
//                .padding(1.dp)
//                .fillMaxWidth()
//                .height(1.dp)
//                .background(light_gray)
//            )
            }
        }

    }
}

