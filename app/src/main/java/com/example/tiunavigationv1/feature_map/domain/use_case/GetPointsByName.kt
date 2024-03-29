package com.example.tiunavigationv1.feature_map.domain.use_case

import com.example.tiunavigationv1.feature_map.domain.model.Point
import com.example.tiunavigationv1.feature_map.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow

class GetPointsByName(
    private val repository: MapRepository
) {
    operator fun invoke(name: String, buildingId: Long): Flow<List<Point>> {
        return repository.getPointByName(name, buildingId)
    }
}