package com.example.tentativarestic.entities

import androidx.room.Entity

@Entity(
    tableName = "person_projeto",
    primaryKeys = ["person_id", "projeto_id"]
)
data class PersonProjeto(
    val person_id: Long,
    val projeto_id: Long,
    val finalizado: Boolean?,
    val confirmou: Boolean?
)
