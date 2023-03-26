package com.example.tiunavigationv1.feature_map.presentation.main

import com.example.tiunavigationv1.feature_map.domain.model.Building

sealed class MainScreenEvent {

    data class EnteredAddress(val address: String): MainScreenEvent()

//    data class ChangeAddressFocus(val focusState: FocusState): MainScreenEvent()

    data class ReverseIsFavoriteField(val buildingId: Long): MainScreenEvent()

}