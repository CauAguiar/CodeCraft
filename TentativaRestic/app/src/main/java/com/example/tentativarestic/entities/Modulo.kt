package com.example.tentativarestic.entities

// Modulo.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "modulo")
data class Modulo(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val descricao: String,
    val nome: String,
    val ordem: Int,
    val unidade_id: Long
)


