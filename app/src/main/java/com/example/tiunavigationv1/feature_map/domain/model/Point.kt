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

//INSERT INTO points (id, point_name, building_id, floor_id, path_id, point_type, point_parameter, in_path_id, x, y, node_id) VALUES
//(1, 'Path1Pt1', 1, 1, 1, 'PATH', 'LINE', 0, 0.1, 0.1, NULL),
//(2, 'Path1Pt2', 1, 1, 1, 'PATH', 'LINE', 1, 0.4, 0.15, NULL),
//(3, 'Path1Pt3', 1, 1, 1, 'PATH', 'LINE', 2, 0.3, 0.4, NULL),
//(4, 'Path1Pt4', 1, 1, 1, 'PATH', 'LINE', 3, 0.05, 0.35, NULL),
//
//(5, 'Path2Pt1', 1, 1, 2, 'PATH', 'LINE', 0, 0.6, 0.1, NULL),
//(6, 'Path2Pt2', 1, 1, 2, 'PATH', 'LINE', 1, 0.9, 0.15, NULL),
//(7, 'Path2Pt3', 1, 1, 2, 'PATH', 'LINE', 2, 0.8, 0.4, NULL),
//(8, 'Path2Pt4', 1, 1, 2, 'PATH', 'LINE', 3, 0.55, 0.35, NULL),
//
//(9, 'Path3Pt1', 1, 1, 3, 'PATH', 'LINE', 0, 0.2, 0.6, NULL),
//(10, 'Path3Pt2', 1, 1, 3, 'PATH', 'LINE', 1, 0.5, 0.65, NULL),
//(11, 'Path3Pt3', 1, 1, 3, 'PATH', 'LINE', 2, 0.4, 0.9, NULL),
//(12, 'Path3Pt4', 1, 1, 3, 'PATH', 'LINE', 3, 0.1, 0.85, NULL),
//
//(13, 'Object1', 1, 1, NULL, 'OBJECT', NULL, NULL, 0.9, 0.9, 4),
//(14, 'Object2', 1, 1, NULL, 'OBJECT', NULL, NULL, 0.1, 0.9, 5),
//(15, 'Object3', 1, 1, NULL, 'OBJECT', NULL, NULL, 0.9, 0.1, 6);

//INSERT INTO points (id, point_name, building_id, floor_id, path_id, point_type, point_parameter, in_path_id, x, y, node_id) VALUES
//(1, 'буфет', 1, 1, NULL, 'OBJECT', NULL, NULL, 0.5, 0.2, 1),
//(2, 'библиотека', 1, 1, NULL, 'OBJECT', NULL, NULL, 0.8, 0.4, 2),
//(3, 'столовая', 1, 1, NULL, 'OBJECT', NULL, NULL, 0.3, 0.4, 3),
//(4, 'что нибудь', 1, 1, NULL, 'OBJECT', NULL, NULL, 0.5, 0.6, 4);