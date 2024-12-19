package com.example.tentativarestic.entities

// RespostasQuestionario.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "respostas_questionario")
data class RespostasQuestionario(
    @PrimaryKey(autoGenerate = true) val id_resposta: Long,
    val id_pergunta: Long,
    val resposta: String,
    val peso: Int
)
