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

//INSERT INTO edges (from_node_id, to_node_id, floor_id) VALUES
//(1, 4, 1),
//(2, 5, 1),
//(3, 6, 1),
//(4, 7, 1),
//(5, 8, 1),
//(6, 9, 1),
//(7, 10, 1),
//(8, 11, 1),
//(10, 12, 1),
//(11, 12, 1);