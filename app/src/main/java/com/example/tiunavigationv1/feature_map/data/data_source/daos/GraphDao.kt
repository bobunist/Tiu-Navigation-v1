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


    @Query("SELECT * FROM nodes WHERE floor_id = :floorId")
    suspend fun getNodesByFloor(floorId: Long): List<Node>


    @Query("SELECT * FROM nodes WHERE id = :nodeId")
    fun getNodeById(nodeId: Long): Flow<Node>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEdge(edge: Edge): Long

    @Update
    suspend fun updateEdge(edge: Edge)

    @Delete
    suspend fun deleteEdge(edge: Edge)

    @Query("SELECT * FROM edges WHERE floor_id = :floorId")
    suspend fun getEdgesByFloor(floorId: Long): List<Edge>

    @Query("SELECT * FROM edges WHERE id = :edgeId")
    suspend fun getEdgeById(edgeId: Long): Edge

}