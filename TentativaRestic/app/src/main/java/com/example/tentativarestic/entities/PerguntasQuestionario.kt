package com.example.tentativarestic.entities

// PerguntasQuestionario.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "perguntas_questionario")
data class PerguntasQuestionario(
    @PrimaryKey(autoGenerate = true) val id_pergunta: Long,
    val pergunta: String,
    val id_curso: Long
)
