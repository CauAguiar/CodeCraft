package com.example.tentativarestic.entities

import androidx.room.Entity

@Entity(
    tableName = "person_licao",
    primaryKeys = ["person_id", "licao_id"]
)
data class PersonLicao(
    val person_id: Long,
    val licao_id: Long,
    val progresso: String?,  // Exemplo: "Em andamento", "Concluído"
    val completou: Boolean?, // Indica se a lição foi completada
    val confirmou: Boolean? // Se a pessoa confirmou que concluiu
)
