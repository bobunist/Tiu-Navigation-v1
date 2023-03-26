package com.example.tiunavigationv1.feature_map.domain.use_case

import com.example.tiunavigationv1.feature_map.domain.model.Building
import com.example.tiunavigationv1.feature_map.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteList(
    private val repository: MapRepository
) {
    operator fun invoke(): Flow<List<Building>> {
        return repository.getFavoriteList()
    }
}