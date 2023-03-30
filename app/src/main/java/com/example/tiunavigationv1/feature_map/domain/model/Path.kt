package com.example.tiunavigationv1.feature_map.domain.model

import androidx.room.*
import com.example.tiunavigationv1.feature_map.domain.util.PathType

@Entity(
    tableName = "paths",
    foreignKeys = [
        ForeignKey(
            entity = Building::class,
            parentColumns = ["id"],
            childColumns = ["building_id"],
            onDelete = ForeignKey.CASCADE
        ),
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
    ]
)
data class Path(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val pathId: Long? = null,

    @ColumnInfo(name = "path_name")
    val pathName: String? = null,

    @ColumnInfo(name = "path_type")
    val pathType: PathType,

    @ColumnInfo(name = "building_id")
    val buildingId: Long,

    @ColumnInfo(name = "floor_id", index = true)
    val floorId: Long,

    @ColumnInfo(name = "node_id")
    val nodeId: Long? = null
)


//INSERT INTO paths (id, path_type, building_id, floor_id, node_id) VALUES
//(1, 'ELEVATOR', 1, 1, 1),
//(2, 'STAIRS', 1, 1, 2),
//(3, 'ROOM', 1, 1, 3);