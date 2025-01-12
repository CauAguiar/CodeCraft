package com.example.tentativarestic.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "person_quiz",
    primaryKeys = ["person_id", "quiz_id"]
)
data class PersonQuiz(
    @SerializedName("personId") val person_id: Long,
    @SerializedName("quizId") val quiz_id: Long,
    val respostaSelecionada: String?,
    val acertou: Boolean?,
    val confirmou: Boolean?
)
