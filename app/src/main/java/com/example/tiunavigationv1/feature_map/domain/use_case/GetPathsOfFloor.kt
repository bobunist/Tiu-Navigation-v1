package com.example.tiunavigationv1.feature_map.domain.use_case

import com.example.tiunavigationv1.feature_map.domain.model.Path
import com.example.tiunavigationv1.feature_map.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow

class GetPathsOfFloor(
    private val repository: MapRepository
) {
    suspend operator fun invoke(floorId: Long): List<Path> {
        return repository.getPathsOfFloor(floorId)
    }
}