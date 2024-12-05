package com.example.tentativarestic.models

data class Modulo(
    val id: Long,
    val nome: String, // Nome do módulo, ex.: "Introdução ao Java"
    val ordem: Int, // A ordem em que o módulo será exibido dentro da unidade
    val descricao: String, // Descrição do módulo
    val unidadeId: Long, // ID da unidade à qual o módulo pertence
    val habilitado: Boolean, // Indica se o módulo está ativo e disponível para os alunos
    val atividades: List<Atividade>? = null // Lista de atividades dentro deste módulo
)