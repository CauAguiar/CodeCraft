package com.example.tentativarestic.entities

// Quiz.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz")
data class Quiz(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val titulo: String,
    val alternativaA: String,
    val alternativaB: String,
    val alternativaC: String,
    val alternativaD: String,
    val enunciado: String,
    val resposta_correta: Char
)
