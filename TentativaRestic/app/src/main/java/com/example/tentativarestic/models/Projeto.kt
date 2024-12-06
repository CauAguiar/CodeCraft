package com.example.tentativarestic.models

data class Projeto(
    val id: Long,
    val nome: String, // Nome do projeto (ex.: "Projeto de Desenvolvimento de Software")
    val descricao: String, // Descrição do projeto
    val linkRepositorio: String?, // Link do repositório (pode ser nulo)
    val tecnologias: String? // Tecnologias utilizadas no projeto (pode ser nulo)
)
