package com.example.tentativarestic.entities

import androidx.room.Entity

@Entity(
    tableName = "person_quiz",
    primaryKeys = ["person_id", "quiz_id"]
)
data class PersonQuiz(
    val person_id: Long,
    val quiz_id: Long,
    val respostaSelecionada: String?,
    val acertou: Boolean?,
    val confirmou: Boolean?
)
