package com.example.tiunavigationv1.feature_map.domain.model

import androidx.room.*
import com.example.tiunavigationv1.feature_map.domain.util.PointParameter
import com.example.tiunavigationv1.feature_map.domain.util.PointType

@Entity(
    tableName = "points",
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
            entity = Path::class,
            parentColumns = ["id"],
            childColumns = ["path_id"],
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
data class Point(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val pointId: Long? = null,

    @ColumnInfo(name = "building_id")
    val buildingId: Long,

    @ColumnInfo(name = "floor_id")
    val floorId: Long,

    @ColumnInfo(name = "path_id")
    val pathId: Long?,

    @ColumnInfo(name = "point_name")
    val pointName: String? = null,

    @ColumnInfo(name = "point_type")
    val pointType: PointType,

    @ColumnInfo(name = "point_parameter")
    val pointParameter: PointParameter?,

    @ColumnInfo(name = "in_path_id")
    val inPathId: Int?,

    @ColumnInfo(name = "x")
    val x: Float,

    @ColumnInfo(name = "y")
    val y: Float,

    @ColumnInfo(name = "node_id")
    val nodeId: Long? = null,
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        if (buildingId != other.buildingId) return false
        if (floorId != other.floorId) return false
        if (pathId != other.pathId) return false
        if (pointName != other.pointName) return false
        if (pointType != other.pointType) return false
        if (pointParameter != other.pointParameter) return false
        if (inPathId != other.inPathId) return false
        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = buildingId.hashCode()
        result = 31 * result + floorId.hashCode()
        result = 31 * result + (pathId?.hashCode() ?: 0)
        result = 31 * result + (pointName?.hashCode() ?: 0)
        result = 31 * result + pointType.hashCode()
        result = 31 * result + (pointParameter?.hashCode() ?: 0)
        result = 31 * result + (inPathId ?: 0)
        result = 31 * result + x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}



//INSERT INTO points (id, building_id, floor_id, path_id, point_name, point_type, point_parameter, in_path_id, x, y) VALUES (null, 1, 1, null, "буфет", "OBJECT", null, null, 0.1, 0.1);
//INSERT INTO points (id, building_id, floor_id, path_id, point_name, point_type, point_parameter, in_path_id, x, y) VALUES (null, 1, 1, 1, null, "PATH", "LINE", 1, 0.6, 0.6);