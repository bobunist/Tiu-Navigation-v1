package com.example.tiunavigationv1.feature_map.domain.use_case

import com.example.tiunavigationv1.feature_map.domain.model.Point
import com.example.tiunavigationv1.feature_map.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow

class GetPointsOfFloor(
    private val repository: MapRepository
) {
    suspend operator fun invoke(floorId: Long): List<Point> {
        return repository.getPointsOfFloor(floorId)
    }
}