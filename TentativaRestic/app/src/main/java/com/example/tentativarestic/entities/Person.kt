package com.example.tentativarestic.entities

// Person.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class Person(
    @PrimaryKey val id: Long,
    val age: Int,
    val codigo_confirmacao: String,
    val email: String,
    val facebook_id: String?,
    val gender: String,
    val google_id: String?,
    val name: String,
    val password: String,
    val phone_number: String,
    val second_name: String?,
    val telefone: String?,
    val data_nascimento: String
)
