package com.example.tiunavigationv1.feature_map.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Vertex(
    @PrimaryKey val id: Long,
    val x: Float,
    val y: Float,
)