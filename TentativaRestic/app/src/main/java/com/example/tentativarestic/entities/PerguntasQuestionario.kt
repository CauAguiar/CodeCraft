package com.example.tentativarestic.entities

// PerguntasQuestionario.kt
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "perguntas_questionario")
data class PerguntasQuestionario(
    @SerializedName("idPergunta") @PrimaryKey(autoGenerate = true) val id_pergunta: Long,
    @SerializedName("pergunta") val pergunta: String,
    @SerializedName("idCurso") val id_curso: Long
)
