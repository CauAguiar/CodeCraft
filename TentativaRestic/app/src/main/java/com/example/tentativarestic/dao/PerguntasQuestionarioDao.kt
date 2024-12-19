package com.example.tentativarestic.dao

// PerguntasQuestionarioDao.kt
import androidx.room.*
import com.example.tentativarestic.entities.PerguntasQuestionario
import kotlinx.coroutines.flow.Flow

@Dao
interface PerguntasQuestionarioDao {
    @Query("SELECT * FROM perguntas_questionario")
    fun getAllPerguntas(): Flow<List<PerguntasQuestionario>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPergunta(pergunta: PerguntasQuestionario)

    @Update
    suspend fun updatePergunta(pergunta: PerguntasQuestionario)

    @Delete
    suspend fun deletePergunta(pergunta: PerguntasQuestionario)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerguntas(perguntas: List<PerguntasQuestionario>)
}
