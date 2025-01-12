package com.example.tentativarestic.entities

import androidx.room.Entity

@Entity(
    tableName = "person_exercicio_aberto",
    primaryKeys = ["person_id", "exercicio_aberto_id"]
)
data class PersonExercicioAberto(
    val person_id: Long,
    val exercicio_aberto_id: Long,
    val resposta: String?,
    val confirmou: Boolean?,
    val correto: Boolean?
)
