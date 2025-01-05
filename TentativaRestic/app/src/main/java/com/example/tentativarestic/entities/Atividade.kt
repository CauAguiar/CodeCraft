package com.example.tentativarestic.entities

// Atividade.kt
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "atividade")
data class Atividade(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("atividadeEspecificaId") val atividade_especifica_id: Long,
    @SerializedName("nome") val nome: String,
    @SerializedName("tipo") val tipo: String,
    @SerializedName("moduloId") val modulo_id: Long
)
