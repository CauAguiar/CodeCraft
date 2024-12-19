package com.example.tentativarestic.entities

// Curso.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "curso")
data class Curso(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val categoria: String,
    val descricao: String,
    val duracao_total: Int,
    val icone: String,
    val nivel: String,
    val nome: String
)
