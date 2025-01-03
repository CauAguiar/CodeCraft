package com.example.tentativarestic.entities

// Curso.kt
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "curso")
data class Curso(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("categoria") val categoria: String,
    @SerializedName("descricao") val descricao: String,
    @SerializedName("duracaoTotal") val duracao_total: Int,
    @SerializedName("icone") val icone: String,
    @SerializedName("nivel") val nivel: String,
    @SerializedName("nome") val nome: String
)
