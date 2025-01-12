package com.example.tentativarestic.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "person_atividade",
    primaryKeys = ["person_id", "atividade_id"]
)
data class PersonAtividade(
    @SerializedName("personId") val person_id: Long,
    @SerializedName("atividadeId") val atividade_id: Long,
    val completou: Boolean?,
    val confirmou: Boolean?
)
