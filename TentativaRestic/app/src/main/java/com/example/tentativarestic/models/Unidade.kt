package com.example.tentativarestic.models;

data class Unidade(
        val id: Long,                           // ID da unidade
        val cursoId: Long,                      // ID do curso ao qual a unidade pertence
        val curso: Curso,                       // Objeto Curso relacionado
        val modulos: List<Modulo>,              // Lista de módulos relacionados a esta unidade
        val titulo: String,                     // Título da unidade
        val descricao: String,                  // Descrição da unidade
        val ordem: Int,                         // Ordem da unidade no curso
        val duracaoTotal: Int,                  // Duração total da unidade em minutos
        val habilitada: Boolean,                // Se a unidade está habilitada
        val icone: String?                      // Ícone da unidade (pode ser uma URL ou identificador)
)

