package com.example.tentativarestic.dao

// AtividadeDao.kt
import androidx.room.*
import com.example.tentativarestic.entities.Atividade
import kotlinx.coroutines.flow.Flow

@Dao
interface AtividadeDao {
    @Query("SELECT * FROM atividade")
    fun getAllAtividades(): Flow<List<Atividade>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAtividade(atividade: Atividade)

    @Update
    suspend fun updateAtividade(atividade: Atividade)

    @Delete
    suspend fun deleteAtividade(atividade: Atividade)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAtividades(atividades: List<Atividade>)
}
