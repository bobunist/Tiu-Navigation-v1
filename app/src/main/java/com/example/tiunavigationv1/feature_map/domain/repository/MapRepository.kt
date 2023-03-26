package com.example.tiunavigationv1.feature_map.domain.repository

import com.example.tiunavigationv1.feature_map.domain.model.*
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    fun getBuildingByAddress(address: String) : Flow<List<Building>>

    fun getFavoriteList(): Flow<List<Building>>

    suspend fun getBuildings(): Flow<List<Building>>

    fun getFloor(floorId: Long): Flow<Floor>

    suspend fun getFloorsOfBuilding(buildingId: Long): Flow<List<Floor>>

    suspend fun getPointsOfFloor(floorId: Long): List<Point>

    suspend fun getPathsOfFloor(floorId: Long): List<Path>

    suspend fun reverseIsFavoriteField(buildingId: Long)

    suspend fun getPointByName(name: String, buildingId: Long): Flow<List<Point>>

}