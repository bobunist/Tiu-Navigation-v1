package com.example.tiunavigationv1.feature_map.data.data_source.daos

import androidx.room.*
import com.example.tiunavigationv1.feature_map.domain.model.Edge
import com.example.tiunavigationv1.feature_map.domain.model.Vertex

@Dao
interface GraphDao {

    @Insert
    suspend fun insertVertex(vertex: Vertex)

    @Update
    suspend fun updateVertex(vertex: Vertex)

    @Delete
    suspend fun deleteVertex(vertex: Vertex)

    @Query("SELECT * FROM Vertex WHERE id = :id")
    suspend fun getVertexById(id: Long): Vertex?


    @Insert
    suspend fun insertEdge(edge: Edge)

    @Update
    suspend fun updateEdge(edge: Edge)

    @Delete
    suspend fun deleteEdge(edge: Edge)

    @Query("SELECT * FROM Edge WHERE id = :id")
    suspend fun getEdgeById(id: Long): Edge?
}