package com.example.tentativarestic.dao

// ProjetoDao.kt
import androidx.room.*
import com.example.tentativarestic.entities.Projeto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjetoDao {
    @Query("SELECT * FROM projeto")
    fun getAllProjetos(): Flow<List<Projeto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjeto(projeto: Projeto)

    @Update
    suspend fun updateProjeto(projeto: Projeto)

    @Delete
    suspend fun deleteProjeto(projeto: Projeto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjetos(projetos: List<Projeto>)

    @Query("SELECT * FROM projeto WHERE id = :atividade_especifica_id")
    fun getProjetoById(atividade_especifica_id: Long): Flow<Projeto>
}
