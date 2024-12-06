package com.example.tentativarestic.models

data class ExercicioAberto(
    val id: Long,
    val enunciado: String, // Enunciado do exercício (ex.: "Escreva um algoritmo que...")
    val respostaEsperada: String? // Resposta esperada (opcional)
)
