package com.example.tiunavigationv1.feature_map.domain.model

import androidx.room.*
import com.example.tiunavigationv1.feature_map.domain.util.PathType

@Entity(
    tableName = "paths",
    foreignKeys = [
        ForeignKey(
            entity = Floor::class,
            parentColumns = ["id"],
            childColumns = ["floor_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Node::class,
            parentColumns = ["id"],
            childColumns = ["node_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["floor_id"]),
        Index(value = ["node_id"])
    ]
)
data class Path(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val pathId: Long? = null,

    @ColumnInfo(name = "path_name")
    val pathName: String,

    @ColumnInfo(name = "path_type")
    val pathType: PathType,

    @ColumnInfo(name = "floor_id", index = true)
    val floorId: Long,

    @ColumnInfo(name = "node_id")
    val nodeId: Long? = null
)


//INSERT INTO paths (id, path_name, path_type, floor_id) VALUES (null, "some path", "ROOM", 1);