package com.example.tiunavigationv1.feature_map.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity City
@Entity(tableName = "cities")
data class City(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val cityId: Long? = null,

    @ColumnInfo(name = "city_name")
    val cityName: String
)

//INSERT INTO cities (id, city_name) VALUES (null, "тюмень");