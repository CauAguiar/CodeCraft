package com.example.tentativarestic.dao

// RespostasQuestionarioDao.kt
import androidx.room.*
import com.example.tentativarestic.entities.RespostasQuestionario
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRespostas(respostas: List<RespostasQuestionario>)

    @Query("SELECT * FROM respostas_questionario WHERE id_pergunta IN (SELECT id_pergunta FROM perguntas_questionario WHERE id_curso = :cursoId)")
    suspend fun getRespostasPorCurso(cursoId: Long): List<RespostasQuestionario>
}
