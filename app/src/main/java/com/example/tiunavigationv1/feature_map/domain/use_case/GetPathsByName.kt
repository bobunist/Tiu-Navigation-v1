package com.example.tiunavigationv1.feature_map.domain.use_case

import com.example.tiunavigationv1.feature_map.domain.model.Path
import com.example.tiunavigationv1.feature_map.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow

class GetPathsByName(
    private val repository: MapRepository
) {
    operator fun invoke(name: String, buildingId: Long): Flow<List<Path>> {
        return repository.getPathsByName(name, buildingId)
    }
}