package com.example.tentativarestic.entities

// RespostasUsuarioQuestionario.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "respostas_usuario_questionario")
data class RespostasUsuarioQuestionario(
    @PrimaryKey(autoGenerate = true) val id_resposta_usuario: Long,
    val id_person: Long,
    val id_pergunta: Long,
    val id_resposta: Long,
    val data_resposta: String // Usar String para armazenar timestamps
)

