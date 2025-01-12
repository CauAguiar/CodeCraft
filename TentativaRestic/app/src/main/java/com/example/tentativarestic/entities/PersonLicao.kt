package com.example.tentativarestic.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "person_licao",
    primaryKeys = ["person_id", "licao_id"]
)
data class PersonLicao(
    @SerializedName("personId") val person_id: Long,
    @SerializedName("licaoId") val licao_id: Long,
    val progresso: String?,  // Exemplo: "Em andamento", "Concluído"
    val completou: Boolean?, // Indica se a lição foi completada
    val confirmou: Boolean? // Se a pessoa confirmou que concluiu
)
