package com.example.tentativarestic.entities

// Unidade.kt
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "unidade")
data class Unidade(
    @PrimaryKey(autoGenerate = true) val id: Long,

    @SerializedName("descricao") val descricao: String,

    @SerializedName("duracaoTotal") val duracao_total: Int,  // Modificado para mapear "duracaoTotal" do JSON

    @SerializedName("icone") val icone: String,

    @SerializedName("ordem") val ordem: Int,

    @SerializedName("titulo") val titulo: String,

    @SerializedName("idCurso") val id_curso: Long  // Modificado para mapear "idCurso" do JSON
)

