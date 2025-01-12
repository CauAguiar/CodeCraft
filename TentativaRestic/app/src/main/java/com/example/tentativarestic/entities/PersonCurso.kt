package com.example.tentativarestic.entities

// PersonCurso.kt
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "person_curso",
    primaryKeys = ["id_person", "id_curso"]
)
data class PersonCurso(
    @SerializedName("idPerson") val id_person: Long,
    @SerializedName("idCurso") val id_curso: Long,
    @SerializedName("nivelamento") val nivelamento: String,
    @SerializedName("concluido") val concluido: Boolean,
    @SerializedName("habilitado") val habilitado: Boolean
)
