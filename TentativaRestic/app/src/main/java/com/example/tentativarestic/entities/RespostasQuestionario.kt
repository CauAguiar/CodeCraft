package com.example.tentativarestic.entities

// RespostasQuestionario.kt
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "respostas_questionario")
data class RespostasQuestionario(
    @SerializedName("idResposta") @PrimaryKey(autoGenerate = true) val id_resposta: Long,
    @SerializedName("idPergunta") val id_pergunta: Long,
    @SerializedName("resposta") val resposta: String,
    @SerializedName("peso") val peso: Int
)
