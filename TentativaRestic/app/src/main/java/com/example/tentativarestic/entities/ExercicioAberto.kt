package com.example.tentativarestic.entities

// ExercicioAberto.kt
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "exercicio_aberto")
data class ExercicioAberto(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("enunciado") val enunciado: String,
    @SerializedName("respostaEsperada") val resposta_esperada: String
)
