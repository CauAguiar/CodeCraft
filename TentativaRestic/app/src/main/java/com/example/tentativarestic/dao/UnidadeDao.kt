package com.example.tentativarestic.dao

// UnidadeDao.kt
import androidx.room.*
import com.example.tentativarestic.entities.Unidade
import kotlinx.coroutines.flow.Flow

@Dao
interface UnidadeDao {
    @Query("SELECT * FROM unidade")
    fun getAllUnidades(): Flow<List<Unidade>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnidade(unidade: Unidade)

    @Update
    suspend fun updateUnidade(unidade: Unidade)

    @Delete
    suspend fun deleteUnidade(unidade: Unidade)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnidades(unidades: List<Unidade>)

    @Query("""
        SELECT unidade.*
        FROM unidade
        INNER JOIN curso ON unidade.id_curso = curso.id
        WHERE curso.nome = :cursoNome
    """)
    fun getUnidadesByCursoNome(cursoNome: String): List<Unidade>
}
