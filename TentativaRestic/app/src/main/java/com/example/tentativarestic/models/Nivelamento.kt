package com.example.tentativarestic.models

data class Nivelamento(
    val curso: Long, // ID do curso
    val atividades: List<Atividade>,
    val quiz: List<Quiz>,
    val nivelAluno: Int, // 1 a 5 ou outros níveis definidos
    val nivelamentoConcluido: Boolean // Se o aluno concluiu o nivelamento
)