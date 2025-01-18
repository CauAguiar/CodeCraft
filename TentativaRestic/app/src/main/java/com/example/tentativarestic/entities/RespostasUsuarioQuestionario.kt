package com.example.tentativarestic.entities

// RespostasUsuarioQuestionario.kt
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "respostas_usuario_questionario")
data class RespostasUsuarioQuestionario(
    @SerializedName("idRespostaUsuario") @PrimaryKey(autoGenerate = true) val id_resposta_usuario: Long,
    @SerializedName("idPerson") val id_person: Long,
    @SerializedName("idPergunta") val id_pergunta: Long,
    @SerializedName("idResposta") val id_resposta: Long,
    @SerializedName("dataResposta") val data_resposta: String, // Usar String para armazenar timestamps
    @SerializedName("respostaSelecionada") val resposta_selecionada: Long?,
    @SerializedName("resultado") val resultado: Boolean?,
    @SerializedName("confirmouResposta") val confirmou_resposta: Boolean?
)

