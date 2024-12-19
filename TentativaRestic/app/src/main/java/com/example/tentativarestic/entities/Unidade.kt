package com.example.tentativarestic.entities

// Unidade.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unidade")
data class Unidade(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val descricao: String,
    val duracao_total: Int,
    val icone: String,
    val ordem: Int,
    val titulo: String,
    val id_curso: Long
)
