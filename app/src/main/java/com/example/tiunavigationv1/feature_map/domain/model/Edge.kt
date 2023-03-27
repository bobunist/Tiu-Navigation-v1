package com.example.tiunavigationv1.feature_map.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "edges",
    foreignKeys = [
        ForeignKey(
            entity = Node::class,
            parentColumns = ["id"],
            childColumns = ["from_node_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Node::class,
            parentColumns = ["id"],
            childColumns = ["to_node_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Floor::class,
            parentColumns = ["id"],
            childColumns = ["floor_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["floor_id"])
    ]
)
data class Edge(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    @ColumnInfo(name = "from_node_id")
    val fromNodeId: Long,

    @ColumnInfo(name = "to_node_id")
    val toNodeId: Long,

    @ColumnInfo(name = "floor_id")
    val floorId: Long,
)

