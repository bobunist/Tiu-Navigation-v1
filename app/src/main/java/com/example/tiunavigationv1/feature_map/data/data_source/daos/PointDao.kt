package com.example.tiunavigationv1.feature_map.data.data_source.daos

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tiunavigationv1.feature_map.domain.model.Building
import com.example.tiunavigationv1.feature_map.domain.model.Point
import kotlinx.coroutines.flow.Flow

@Dao
interface PointDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(point: Point)

    @Query("SELECT * FROM points WHERE floor_id=:floorId")
    suspend fun getByFloor(floorId: Long): List<Point>

    @Query("SELECT * FROM points WHERE point_name LIKE :name || '%' AND building_id =:buildingId AND point_type == 'OBJECT'")
    fun getByName(name: String, buildingId: Long): Flow<List<Point>>

    @Delete
    suspend fun delete(point: Point)
}