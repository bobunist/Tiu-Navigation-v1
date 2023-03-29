package com.example.tiunavigationv1.di
import android.app.Application
import androidx.room.Room
import com.example.tiunavigationv1.feature_map.data.data_source.MapDatabase
import com.example.tiunavigationv1.feature_map.data.repository.MapRepositoryImpl
import com.example.tiunavigationv1.feature_map.domain.repository.MapRepository
import com.example.tiunavigationv1.feature_map.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMapDatabase(app: Application): MapDatabase{
        return Room.databaseBuilder(
            app,
            MapDatabase::class.java,
            MapDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMapRepository(db: MapDatabase): MapRepository {
        return MapRepositoryImpl(db.cityDao, db.buildingDao, db.floorDao, db.pathDao, db.pointDao, db.graphDao)
    }

    @Provides
    @Singleton
    fun provideMapUseCases(repository: MapRepository):MapUseCases{
        return MapUseCases(
            getFloorsOfBuilding = GetFloorsOfBuilding(repository),
            getFavoriteList = GetFavoriteList(repository),
            reverseIsFavoriteField = ReverseIsFavoriteField(repository),
            searchBuilding = SearchBuilding(repository),
            getPointsOfFloor = GetPointsOfFloor(repository),
            getPathsOfFloor = GetPathsOfFloor(repository),
            getFloor = GetFloor(repository),
            getPointsByName = GetPointsByName(repository),
            getEdgesByFloor = GetEdgesByFloor(repository),
            getNodesByFloor = GetNodesByFloor(repository)
        )
    }
}