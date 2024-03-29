package com.example.tiunavigationv1.feature_map.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tiunavigationv1.feature_map.data.data_source.daos.*
import com.example.tiunavigationv1.feature_map.domain.model.*

@Database(entities = [
    City::class,
    Building::class,
    Floor::class,
    Point::class,
    Path::class,
    Node::class,
    Edge::class], version = 1)
abstract class MapDatabase : RoomDatabase() {

    abstract val cityDao: CityDao
    abstract val buildingDao: BuildingDao
    abstract val floorDao: FloorDao
    abstract val pointDao: PointDao
    abstract val pathDao: PathDao
    abstract val graphDao: GraphDao


    companion object {
        const val DATABASE_NAME = "map_db"
    }
}
