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

//INSERT INTO nodes (id, x, y, floor_id) VALUES
//(1, 0.1, 0.1, 1),
//(2, 0.4, 0.1, 1),
//(3, 0.7, 0.1, 1),
//(4, 0.1, 0.4, 1),
//(5, 0.4, 0.4, 1),
//(6, 0.7, 0.4, 1),
//(7, 0.25, 0.7, 1),
//(8, 0.5, 0.7, 1),
//(9, 0.75, 0.7, 1),
//(10, 0.1, 0.7, 1),
//(11, 0.7, 0.7, 1),
//(12, 0.4, 0.25, 1);