package com.example.tiunavigationv1.feature_map.domain.use_case

import com.example.tiunavigationv1.feature_map.domain.repository.MapRepository

class ReverseIsFavoriteField(
    private val repository: MapRepository
) {
     suspend operator fun invoke(buildingId: Long) {
        repository.reverseIsFavoriteField(buildingId)
    }
}