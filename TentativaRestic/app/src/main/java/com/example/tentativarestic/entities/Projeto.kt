package com.example.tentativarestic.entities

// Projeto.kt
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "projeto")
data class Projeto(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("descricao") val descricao: String,
    @SerializedName("linkRepositorio") val link_repositorio: String,
    @SerializedName("nome") val nome: String,
    @SerializedName("tecnologias") val tecnologias: String
)
