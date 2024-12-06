package com.example.tentativarestic.models

data class Quiz(
    val id: Long,
    val titulo: String, // Título do quiz (ex.: "Quiz sobre Java Básico")
    val alternativaA: String, // Alternativa A
    val alternativaB: String, // Alternativa B
    val alternativaC: String, // Alternativa C
    val alternativaD: String, // Alternativa D
    val respostaCerta: String, // Resposta correta (ex.: "A", "B", "C", ou "D")
    val enunciado: String // Texto da pergunta (ex.: "Qual é a linguagem de programação mais usada?")
)
