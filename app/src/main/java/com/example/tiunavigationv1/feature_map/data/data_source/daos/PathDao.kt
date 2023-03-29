package com.example.tiunavigationv1.feature_map.data.data_source.daos

import androidx.room.Dao
import androidx.room.*
import com.example.tiunavigationv1.feature_map.domain.model.Path
import kotlinx.coroutines.flow.Flow

@Dao
interface PathDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(path: Path)

    @Query("SELECT * FROM paths")
    fun getAll(): Flow<List<Path>>

    @Query("SELECT * FROM paths WHERE id=:id")
    fun getById(id: Long): Flow<Path>

    @Query("SELECT * FROM paths WHERE floor_id=:floorId")
    suspend fun getByFloor(floorId: Long): List<Path>

    @Query("SELECT * FROM paths WHERE path_name LIKE :name || '%' AND building_id =:buildingId ")
    fun getByName(name: String, buildingId: Long): Flow<List<Path>>

    @Delete
    suspend fun delete(path: Path)
}