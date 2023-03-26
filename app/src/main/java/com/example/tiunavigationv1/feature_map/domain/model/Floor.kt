package com.example.tiunavigationv1.feature_map.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

// Entity Floor
@Entity(tableName = "floors",
    foreignKeys = [ForeignKey(
        entity = Building::class,
        parentColumns = ["id"],
        childColumns = ["building_id"],
        onDelete = ForeignKey.CASCADE)])
data class Floor(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var floorId: Long? = null,

    @ColumnInfo(name = "floor_name")
    val floorName: String,

    @ColumnInfo(name = "building_id", index = true)
    val buildingId: Long
)

//INSERT INTO floors (id, floor_name, building_id) VALUES (null, "1", 1);
//INSERT INTO floors (id, floor_name, building_id) VALUES (null, "2", 1);