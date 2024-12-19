package com.example.tentativarestic.entities

// PersonCurso.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "person_curso",
    primaryKeys = ["id_person", "id_curso"]
)
data class PersonCurso(
    val id_person: Long,
    val id_curso: Long,
    val nivelamento: String,
    val concluido: Boolean,
    val habilitado: Boolean
)
