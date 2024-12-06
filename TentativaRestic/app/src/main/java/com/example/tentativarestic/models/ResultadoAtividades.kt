package com.example.tentativarestic.models

data class ResultadoAtividades(
    val quiz: List<Quiz>? = null,
    val projeto: List<Projeto>? = null,
    val exercicio_aberto: List<ExercicioAberto>? = null,
    val video: List<Video>? = null,
    val desconhecido: List<Atividade>?
)