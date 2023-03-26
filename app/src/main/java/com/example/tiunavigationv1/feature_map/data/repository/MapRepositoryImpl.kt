package com.example.tiunavigationv1.feature_map.data.repository

import com.example.tiunavigationv1.feature_map.data.data_source.daos.*
import com.example.tiunavigationv1.feature_map.domain.model.*
import com.example.tiunavigationv1.feature_map.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow

class MapRepositoryImpl(
    private val cityDao: CityDao,
    private val buildingDao: BuildingDao,
    private val floorDao: FloorDao,
    private val pathDao: PathDao,
    private val pointDao: PointDao,
): MapRepository {

    override fun getBuildingByAddress(address: String): Flow<List<Building>> {
        return buildingDao.getByAddress(address)
    }

    override suspend fun getBuildings(): Flow<List<Building>> {
        return buildingDao.getAll()
    }

    override fun getFavoriteList(): Flow<List<Building>> {
        return buildingDao.getFavoriteList()
    }

    override suspend fun getFloorsOfBuilding(buildingId: Long): Flow<List<Floor>> {
        return floorDao.getByBuildingId(buildingId)
    }

    override suspend fun getPointsOfFloor(floorId: Long): List<Point> {
        return pointDao.getByFloor(floorId)
    }

    override suspend fun getPathsOfFloor(floorId: Long): List<Path> {
        return pathDao.getByFloor(floorId)
    }

    override suspend fun reverseIsFavoriteField(buildingId: Long) {
        buildingDao.reverseIsFavoriteField(buildingId)
    }

    override fun getFloor(floorId: Long): Flow<Floor> {
        return floorDao.getById(floorId)
    }

    override suspend fun getPointByName(name: String, buildingId: Long): Flow<List<Point>> {
        return pointDao.getByName(name, buildingId)
    }
}