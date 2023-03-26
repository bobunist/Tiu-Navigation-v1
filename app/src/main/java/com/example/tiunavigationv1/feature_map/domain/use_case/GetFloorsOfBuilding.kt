package com.example.tiunavigationv1.feature_map.domain.use_case

import com.example.tiunavigationv1.feature_map.domain.model.Floor
import com.example.tiunavigationv1.feature_map.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow


class GetFloorsOfBuilding(
    private val repository: MapRepository
) {

    suspend operator fun invoke(buildingId: Long): Flow<List<Floor>> {
        return repository.getFloorsOfBuilding(buildingId)
    }
}