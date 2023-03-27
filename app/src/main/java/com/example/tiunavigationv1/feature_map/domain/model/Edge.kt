package com.example.tiunavigationv1.feature_map.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Edge(
    @PrimaryKey val id: Long,
    val vertex1Id: Long,
    val vertex2Id: Long,
    val weight: Float,
    // дополнительные атрибуты
)