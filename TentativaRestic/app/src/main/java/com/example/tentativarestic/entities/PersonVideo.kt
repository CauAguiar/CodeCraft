package com.example.tentativarestic.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "person_video",
    primaryKeys = ["person_id", "video_id"]
)
data class PersonVideo(
    @SerializedName("personId") val person_id: Long,
    @SerializedName("videoId") val video_id: Long,
    val assistido: Boolean?,
    val confirmou: Boolean?
)
