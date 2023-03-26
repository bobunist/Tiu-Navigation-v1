package com.example.tiunavigationv1.feature_map.data.data_source.daos

import androidx.room.Dao
import androidx.room.*
import com.example.tiunavigationv1.feature_map.domain.model.Floor
import kotlinx.coroutines.flow.Flow

@Dao
interface FloorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(floor: Floor)

    @Query("SELECT * FROM floors")
    fun getAll(): Flow<List<Floor>>

    @Query("SELECT * FROM floors WHERE id=:floorId")
    fun getById(floorId: Long): Flow<Floor>

    @Query("SELECT * FROM floors WHERE building_id=:buildingId")
    fun getByBuildingId(buildingId: Long): Flow<List<Floor>>

    @Delete
    suspend fun delete(floor: Floor)
}