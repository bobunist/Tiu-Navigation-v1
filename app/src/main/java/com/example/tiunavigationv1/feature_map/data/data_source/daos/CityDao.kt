package com.example.tiunavigationv1.feature_map.data.data_source.daos

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tiunavigationv1.feature_map.domain.model.City
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: City)

    @Query("SELECT * FROM cities")
    fun getAll(): Flow<List<City>>

    @Query("SELECT * FROM cities WHERE id=:id")
    fun getById(id: Long): Flow<City>

    @Delete
    suspend fun delete(city: City)
}