package com.example.tiunavigationv1.feature_map.presentation.map

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class SearchListState(
    var searchList: List<MapElement> = mutableStateListOf(),
    val isStartList: MutableState<Boolean> = mutableStateOf(true),
    val isSearchListVisible: MutableState<Boolean> = mutableStateOf(false)
)
