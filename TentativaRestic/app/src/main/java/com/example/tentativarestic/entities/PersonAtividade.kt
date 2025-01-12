package com.example.tentativarestic.entities

import androidx.room.Entity

@Entity(
    tableName = "person_atividade",
    primaryKeys = ["person_id", "atividade_id"]
)
data class PersonAtividade(
    val person_id: Long,
    val atividade_id: Long,
    val completou: Boolean?,
    val confirmou: Boolean?
)
