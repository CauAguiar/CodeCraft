package com.example.tentativarestic.entities

// Modulo.kt
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "modulo")
data class Modulo(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("descricao") val descricao: String,
    @SerializedName("nome") val nome: String,
    @SerializedName("ordem") val ordem: Int,
    @SerializedName("unidadeId") val unidade_id: Long
)


