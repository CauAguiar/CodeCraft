package com.example.tentativarestic.dao

// RespostasUsuarioQuestionarioDao.kt
import androidx.room.*
import com.example.tentativarestic.entities.RespostasUsuarioQuestionario
import kotlinx.coroutines.flow.Flow

@Dao
interface RespostasUsuarioQuestionarioDao {
    @Query("SELECT * FROM respostas_usuario_questionario")
    fun getAllRespostasUsuario(): Flow<List<RespostasUsuarioQuestionario>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRespostaUsuario(respostaUsuario: RespostasUsuarioQuestionario)

    @Update
    suspend fun updateRespostaUsuario(respostaUsuario: RespostasUsuarioQuestionario)

    @Delete
    suspend fun deleteRespostaUsuario(respostaUsuario: RespostasUsuarioQuestionario)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRespostasUsuario(respostasUsuario: List<RespostasUsuarioQuestionario>)

    @Query("SELECT * FROM respostas_usuario_questionario WHERE id_person = :personId AND id_pergunta = :quizId")
    fun getNivelamentoByPersonIdAndQuizId(personId: Long, quizId: Long): Flow<RespostasUsuarioQuestionario?>


    @Query(
        """
    SELECT * 
    FROM respostas_usuario_questionario 
    WHERE id_pergunta IN (:todasPerguntasIds) 
    AND confirmou_resposta = 1
"""
    )
    fun getRespostasByPerguntasIds(todasPerguntasIds: List<Long>): List<RespostasUsuarioQuestionario>

    @Query(
        """
    SELECT * 
    FROM respostas_usuario_questionario 
    WHERE id_pergunta IN (:todasPerguntasIds)
"""
    )
    fun getRespostasByPerguntasIds2(todasPerguntasIds: List<Long>): List<RespostasUsuarioQuestionario>

}
