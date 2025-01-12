package com.example.tentativarestic.entities

import androidx.room.Entity

@Entity(tableName = "licao")
data class Licao(
    val id: Long,
    val titulo: String,
    val descricao: String,
    val conteudo: String,
)
