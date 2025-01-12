package com.example.tentativarestic.entities

import androidx.room.Entity

@Entity(
    tableName = "person_video",
    primaryKeys = ["person_id", "video_id"]
)
data class PersonVideo(
    val person_id: Long,
    val video_id: Long,
    val assistido: Boolean?,
    val confirmou: Boolean?
)
