package com.example.tentativarestic.dao

// AtividadeDao.kt
import androidx.room.*
import com.example.tentativarestic.entities.Atividade
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

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

    @Query("SELECT * FROM atividade WHERE modulo_id = :moduloId")
    fun getAtividadesByModuloId(moduloId: Long): Flow<List<Atividade>>
}
