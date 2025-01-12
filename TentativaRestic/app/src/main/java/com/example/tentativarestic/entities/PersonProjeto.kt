package com.example.tentativarestic.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "person_projeto",
    primaryKeys = ["person_id", "projeto_id"]
)
data class PersonProjeto(
    @SerializedName("personId") val person_id: Long,
    @SerializedName("projetoId") val projeto_id: Long,
    val finalizado: Boolean?,
    val confirmou: Boolean?
)
