package com.example.tentativarestic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tentativarestic.data.AppDatabase
import com.example.tentativarestic.entities.Quiz
import com.example.tentativarestic.entities.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VideoViewModel(private val database: AppDatabase) : ViewModel() {
    private val videoDao = database.videoDao()

    val videos: StateFlow<List<Video>> = videoDao.getAllVideos()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addVideo(video: Video) {
        viewModelScope.launch {
            videoDao.insertVideo(video)
        }
    }

    fun updateVideo(video: Video) {
        viewModelScope.launch {
            videoDao.updateVideo(video)
        }
    }

    fun deleteVideo(video: Video) {
        viewModelScope.launch {
            videoDao.deleteVideo(video)
        }
    }

    suspend fun getVideoById(atividade_especifica_id: Long): Flow<Video> {
        return videoDao.getVideoById(atividade_especifica_id)
    }

}