package com.example.tiunavigationv1.feature_map.data.data_source.daos

import androidx.room.*
import com.example.tiunavigationv1.feature_map.domain.model.Edge
import com.example.tiunavigationv1.feature_map.domain.model.Node
import kotlinx.coroutines.flow.Flow

@Dao
interface GraphDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNode(node: Node): Long

    @Update
    suspend fun updateNode(node: Node)

    @Delete
    suspend fun deleteNode(node: Node)

    @Query("SELECT * FROM nodes")
    fun getAllNodes(): Flow<List<Node>>

    @Query("SELECT * FROM nodes WHERE floor_id = :floorId")
    fun getNodesByFloor(floorId: Long): Flow<List<Node>>


    @Query("SELECT * FROM nodes WHERE nodeId = :nodeId")
    fun getNodeById(nodeId: Long): Flow<Node>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEdge(edge: Edge): Long

    @Update
    suspend fun updateEdge(edge: Edge)

    @Delete
    suspend fun deleteEdge(edge: Edge)

    @Query("SELECT * FROM edges")
    fun getAllEdges(): Flow<List<Edge>>

    @Query("SELECT * FROM edges WHERE edgeId = :edgeId")
    fun getEdgeById(edgeId: Long): Flow<Edge>

}