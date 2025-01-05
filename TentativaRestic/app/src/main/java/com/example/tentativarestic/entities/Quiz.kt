package com.example.tentativarestic.entities

// Quiz.kt
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quiz")
data class Quiz(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("alternativaA") val alternativaA: String,
    @SerializedName("alternativaB") val alternativaB: String,
    @SerializedName("alternativaC") val alternativaC: String,
    @SerializedName("alternativaD") val alternativaD: String,
    @SerializedName("enunciado") val enunciado: String,
    @SerializedName("respostaCorreta") val resposta_correta: Char
)
