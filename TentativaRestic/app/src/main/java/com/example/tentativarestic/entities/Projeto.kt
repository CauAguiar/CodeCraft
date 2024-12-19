package com.example.tentativarestic.entities

// Projeto.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projeto")
data class Projeto(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val descricao: String,
    val link_repositorio: String,
    val nome: String,
    val tecnologias: String
)
