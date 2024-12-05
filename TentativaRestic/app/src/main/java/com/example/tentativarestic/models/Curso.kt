package com.example.tentativarestic.models


data class Curso(
    val id: Long = 0,
    val nome: String, // Nome do curso, ex.: "Java para Iniciantes"
    val descricao: String, // Descrição geral do curso
    val nivel: String, // Nível do curso (ex.: "Básico", "Intermediário", "Avançado")
    val categoria: String, // Categoria do curso (ex.: "Linguagens de Programação", "Banco de Dados")
    val icone: String?, // URL ou identificador de ícone representando o curso
    val duracaoTotal: Int, // Duração total do curso em minutos
    val habilitado: Boolean, // Indica se o curso está ativo e disponível
    val unidades: List<Unidade>? = null // Relação com as unidades do curso (se aplicável)
)
