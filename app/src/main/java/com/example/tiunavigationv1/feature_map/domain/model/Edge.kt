package com.example.tiunavigationv1.feature_map.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "edges",
    foreignKeys = [
        ForeignKey(
            entity = Node::class,
            parentColumns = ["nodeId"],
            childColumns = ["fromNodeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Node::class,
            parentColumns = ["nodeId"],
            childColumns = ["toNodeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["fromNodeId"]),
        Index(value = ["toNodeId"])
    ]
)
data class Edge(
    @PrimaryKey(autoGenerate = true) val edgeId: Long = 0L,
    val fromNodeId: Long,
    val toNodeId: Long,
    val weight: Float?,
)

