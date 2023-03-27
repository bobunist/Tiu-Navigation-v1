package com.example.tiunavigationv1.feature_map.domain.use_case

import com.example.tiunavigationv1.feature_map.domain.model.Edge
import com.example.tiunavigationv1.feature_map.domain.repository.MapRepository

class GetEdgesByFloor(
    private val repository: MapRepository
) {
    suspend operator fun invoke(floorId: Long): List<Edge> {
        return repository.getEdgesByFloor(floorId)
    }
}