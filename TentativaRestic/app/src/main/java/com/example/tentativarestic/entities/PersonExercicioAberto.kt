package com.example.tentativarestic.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "person_exercicio_aberto",
    primaryKeys = ["person_id", "exercicio_aberto_id"]
)
data class PersonExercicioAberto(
    @SerializedName("personId") val person_id: Long,
    @SerializedName("exercicioAbertoId") val exercicio_aberto_id: Long,
    val resposta: String?,
    val confirmou: Boolean?,
    val correto: Boolean?
)
