package com.example.tiunavigationv1.feature_map.presentation.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiunavigationv1.feature_map.domain.model.Floor
import com.example.tiunavigationv1.ui.theme.gray
import com.example.tiunavigationv1.ui.theme.light_gray


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FloorBar(
    modifier: Modifier = Modifier,
    floorList: List<Floor>,
    onClick: (Long) -> Unit,
    currentFloor: Floor? = null
){
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,

    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .background(gray)
        ) {
            items(floorList){ item ->
                val color = remember {
                    mutableStateOf(light_gray)
                }
                color.value = if (item == currentFloor) colors.primary else light_gray
                Card(
                    modifier = Modifier
                        .padding(5.dp, 10.dp)
                        .fillMaxWidth()
                        .size(45.dp),
                    shape = MaterialTheme.shapes.large,
                    onClick = { onClick(item.floorId!!) }
                ) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(color.value),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = item.floorName,
                            style = MaterialTheme.typography.body1)
                    }
                }

            }
        }

    }
}

//@Preview(showBackground = true)
//@Composable
//fun Prev(){
//    Column(modifier = Modifier
//        .background(light_gray)
//        .fillMaxSize()) {
//
//    }
//    FloorBar(
//        Modifier
//            .height(400.dp)
//            .width(30.dp),
//        listOf(
//            Floor(null, "1", 1),
//            Floor(null, "2", 1),
//            Floor(null, "3", 1),
//            Floor(null, "4", 1),
//            Floor(null, "5", 1)
//        ),
//        onClick = viewModel()
//    )
//}