package com.example.tentativarestic.dao

// VideoDao.kt
import androidx.room.*
import com.example.tentativarestic.entities.Video
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {
    @Query("SELECT * FROM video")
    fun getAllVideos(): Flow<List<Video>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(video: Video)

    @Update
    suspend fun updateVideo(video: Video)

    @Delete
    suspend fun deleteVideo(video: Video)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<Video>)

    @Query("SELECT * FROM video WHERE id = :atividade_especifica_id")
    fun getVideoById(atividade_especifica_id: Long): Flow<Video>
}
