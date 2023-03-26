package com.example.tiunavigationv1.feature_map.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//enum class Addresses(address: String) {
//    Building1("Володарского 38"), Building2("Мельникайте 72"),
//    Building3("50 лет Октября 38"), Building4("Володарского 56"),
//    Building5("Мельникайте 72/1"), Building6("Луначарского 2/6"),
//    Building7("Мельникайте 70"), Building8("Луначарского 8"),
//    Building8Fraction1("Луначарского 2"), Building10("Энергетиков 44"),
//    Building13("Холодильная 85"), Building16("50 лет Октября 62"),
//    Building17("Киевская 78/1")
//}

// Entity Building
@Entity(tableName = "buildings",
    foreignKeys = [ForeignKey(
        entity = City::class,
        parentColumns = ["id"],
        childColumns = ["city_id"],
        onDelete = ForeignKey.CASCADE)])
data class Building(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val buildingId: Long? = null,

    @ColumnInfo(name = "building_address")
    val buildingAddress: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,

    @ColumnInfo(name = "city_id", index = true)
    val cityId: Long
)
//INSERT INTO buildings (id, building_address, is_favorite, city_id) VALUES (null, "50 лет октября 70", 1, 1);