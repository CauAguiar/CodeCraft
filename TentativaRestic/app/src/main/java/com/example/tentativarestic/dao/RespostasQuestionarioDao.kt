package com.example.tentativarestic.dao

// RespostasQuestionarioDao.kt
import androidx.room.*
import com.example.tentativarestic.entities.RespostasQuestionario
import kotlinx.coroutines.flow.Flow

@Dao
interface RespostasQuestionarioDao {
    @Query("SELECT * FROM respostas_questionario")
    fun getAllRespostas(): Flow<List<RespostasQuestionario>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResposta(resposta: RespostasQuestionario)

    @Update
    suspend fun updateResposta(resposta: RespostasQuestionario)

    @Delete
    suspend fun deleteResposta(resposta: RespostasQuestionario)
}
