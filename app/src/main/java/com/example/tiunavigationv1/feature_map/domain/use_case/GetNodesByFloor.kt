package com.example.tiunavigationv1.feature_map.domain.use_case

import com.example.tiunavigationv1.feature_map.domain.model.Node
import com.example.tiunavigationv1.feature_map.domain.repository.MapRepository


class GetNodesByFloor(
    private val repository: MapRepository
) {
    suspend operator fun invoke(floorId: Long): List<Node> {
        return repository.getNodesByFloor(floorId)
    }
}