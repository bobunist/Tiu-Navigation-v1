package com.example.tiunavigationv1.feature_map.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "nodes",
    foreignKeys = [
        ForeignKey(
            entity = Floor::class,
            parentColumns = ["id"],
            childColumns = ["floor_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Node(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val x: Float,

    val y: Float,

    @ColumnInfo(name = "floor_id")
    val floorId: Long,
)
