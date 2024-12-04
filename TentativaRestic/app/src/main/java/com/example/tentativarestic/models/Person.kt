package com.example.tentativarestic.models

data class Person(
    val id: Long? = null,                   // ID é opcional e pode ser nulo
    val email: String,                      // Email (único e obrigatório)
    val password: String,                   // Senha
    val name: String,                       // Nome
    val telefone: String,                   // Telefone (único e obrigatório)
    val secondName: String? = null,         // Nome secundário (opcional)
    val age: Int? = null,                   // Idade (opcional)
    val gender: String? = null,             // Gênero (opcional)
    val dataNascimento: String? = null,     // Data de nascimento (opcional)
    val facebookId: String? = null,         // ID do Facebook (opcional)
    val googleId: String? = null,           // ID do Google (opcional)
    val codigoConfirmacao: String? = null   // Código de confirmação (opcional)
)

