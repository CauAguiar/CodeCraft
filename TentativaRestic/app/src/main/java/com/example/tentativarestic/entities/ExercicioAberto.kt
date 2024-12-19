package com.example.tentativarestic.entities

// ExercicioAberto.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercicio_aberto")
data class ExercicioAberto(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val enunciado: String,
    val resposta_esperada: String
)
