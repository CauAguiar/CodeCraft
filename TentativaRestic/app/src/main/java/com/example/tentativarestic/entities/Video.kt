package com.example.tentativarestic.entities

// Video.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video")
data class Video(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val descricao: String,
    val titulo: String,
    val url: String
)
