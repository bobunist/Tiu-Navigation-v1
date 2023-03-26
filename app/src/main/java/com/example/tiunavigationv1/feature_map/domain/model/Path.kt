package com.example.tiunavigationv1.feature_map.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.tiunavigationv1.feature_map.domain.util.PathType

// Entity Room
@Entity(tableName = "paths",
    foreignKeys = [ForeignKey(
        entity = Floor::class,
        parentColumns = ["id"],
        childColumns = ["floor_id"],
        onDelete = ForeignKey.CASCADE)])
data class Path(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val pathId: Long? = null,

    @ColumnInfo(name = "path_name")
    val pathName: String,

    @ColumnInfo(name = "path_type")
    val pathType: PathType,

    @ColumnInfo(name = "floor_id", index = true)
    val floorId: Long
)

//INSERT INTO paths (id, path_name, path_type, floor_id) VALUES (null, "some path", "ROOM", 1);