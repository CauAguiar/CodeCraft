package com.example.tentativarestic.models

data class Quiz(
    val id: Long,
    val titulo: String, // Título do quiz (ex.: "Quiz sobre Java Básico")
    val alternativaA: String, // Alternativa A
    val alternativaB: String, // Alternativa B
    val alternativaC: String, // Alternativa C
    val alternativaD: String, // Alternativa D
    val respostaCerta: Int, // Resposta correta (ex.: "A", "B", "C", ou "D")
    val enunciado: String, // Texto da pergunta (ex.: "Qual é a linguagem de programação mais usada?")
    var respostaSelecionada: Int? = null, // Resposta selecionada pelo usuário (pode ser nulo)
    var resultado: Boolean? = null, // Resultado do quiz (pode ser nulo)
    var confirmouResposta: Boolean = false // Indica se o usuário confirmou a resposta (pode ser nulo)
)
