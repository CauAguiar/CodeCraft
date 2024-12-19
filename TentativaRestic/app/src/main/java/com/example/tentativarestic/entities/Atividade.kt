package com.example.tentativarestic.entities

// Atividade.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "atividade")
data class Atividade(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val atividade_especifica_id: Long,
    val nome: String,
    val tipo: String,
    val modulo_id: Long
)
