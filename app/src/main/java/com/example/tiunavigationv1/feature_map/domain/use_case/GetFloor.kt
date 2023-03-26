package com.example.tiunavigationv1.feature_map.domain.use_case

import com.example.tiunavigationv1.feature_map.domain.model.Floor
import com.example.tiunavigationv1.feature_map.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow

class GetFloor(
    private val repository: MapRepository
) {
    operator fun invoke(floorId: Long): Flow<Floor> {
        return repository.getFloor(floorId)
    }
}