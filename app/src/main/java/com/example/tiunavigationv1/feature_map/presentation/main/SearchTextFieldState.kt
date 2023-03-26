package com.example.tiunavigationv1.feature_map.presentation.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class SearchTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isListVisible: MutableState<Boolean>,
)