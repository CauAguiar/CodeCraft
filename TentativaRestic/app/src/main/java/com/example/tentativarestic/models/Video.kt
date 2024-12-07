package com.example.tentativarestic.models

data class Video(
    val id: Long,
    val titulo: String, // Título do vídeo (ex.: "Introdução ao Java")
    val url: String, // URL do vídeo (ex.: link do YouTube ou arquivo armazenado)
    val descricao: String?, // Descrição do vídeo (opcional)
    var isClicked: Boolean = false
)
