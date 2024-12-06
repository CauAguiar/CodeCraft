package com.example.tentativarestic.models

data class Atividade(
    val id: Long, // ID da atividade, gerado automaticamente
    val nome: String, // Nome da atividade (ex.: "Quiz sobre Java")
    val tipo: String, // Tipo da atividade (pode ser "quiz", "video", "exercicio_aberto", etc.)
    val moduloId: Long, // ID do módulo ao qual a atividade pertence
    val habilitada: Boolean, // Indica se a atividade está disponível
    var concluida: Boolean, // Indica se a atividade foi concluída pelo aluno
    val atividadeEspecificaId: Long? = null, // Armazenará o ID da atividade específica (como Quiz, Video, etc.)
    val acertos: Int? = null, // Para atividades de quiz ou exercícios, número de acertos
    val erros: Int? = null // Para atividades de quiz ou exercícios, número de erros
)