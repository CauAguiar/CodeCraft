package com.example.tentativarestic.models

data class Projeto(
    val id: Long,
    val nome: String, // Nome do projeto (ex.: "Projeto de Desenvolvimento de Software")
    val descricao: String, // Descrição do projeto
    val linkRepositorio: String?, // Link do repositório (pode ser nulo)
    val tecnologias: String?, // Tecnologias utilizadas no projeto (pode ser nulo)
    var resultado: String = "", // Resultado do projeto (pode ser nulo)
    var isValid: Boolean, // Indica se o projeto é válido ou não
    var isLoading: Boolean, // Indica se o projeto está sendo carregado
    var isConfirmed: Boolean,
    var texto: String = "",
)
