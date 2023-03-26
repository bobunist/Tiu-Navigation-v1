package com.example.tiunavigationv1.feature_map.data.data_source.daos

import androidx.room.Dao
import androidx.room.*
import com.example.tiunavigationv1.feature_map.domain.model.Building
import kotlinx.coroutines.flow.Flow

@Dao
interface BuildingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(building: Building)

    @Query("UPDATE buildings SET is_favorite = NOT is_favorite WHERE id=:building_id")
    suspend fun reverseIsFavoriteField(building_id: Long)

    @Query("SELECT * FROM buildings")
    fun getAll(): Flow<List<Building>>

    @Query("SELECT * FROM buildings WHERE id=:id")
    fun getById(id: Long): Flow<Building>

    @Query("SELECT * FROM buildings WHERE building_address LIKE :address || '%'")
    fun getByAddress(address: String): Flow<List<Building>>

    @Query("SELECT * FROM buildings WHERE city_id=:cityId")
    fun getByCity(cityId: Long): Flow<List<Building>>

    @Query("SELECT * FROM buildings WHERE is_favorite = 1")
    fun getFavoriteList(): Flow<List<Building>>

    @Delete
    suspend fun delete(building: Building)
}