package com.example.tentativarestic.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tentativarestic.data.AppDatabase
import com.example.tentativarestic.data.DataRepository
import com.example.tentativarestic.entities.PerguntasQuestionario
import com.example.tentativarestic.entities.RespostasQuestionario
import com.example.tentativarestic.entities.RespostasUsuarioQuestionario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NivelamentoViewModel(private val database: AppDatabase, private val dataRepository: DataRepository) : ViewModel() {
    private val perguntasQuestionarioDao = database.perguntasQuestionarioDao()
    private val respostasQuestionarioDao = database.respostasQuestionarioDao()
    private val respostasUsuarioQuestionarioDao = database.respostasUsuarioQuestionarioDao()

    suspend fun syncPerguntasComRespostas(cursoId : Long) {
        try {
            dataRepository.syncPerguntasComRespostas(cursoId)
        } catch (e: Exception) {
            // Trate a exceção
        }
    }

    data class PerguntaComRespostas(
        val pergunta: PerguntasQuestionario,
        val respostas: List<RespostasQuestionario>
    )

    private val _perguntasComRespostasFlow = MutableStateFlow<List<PerguntaComRespostas>>(emptyList())
    val perguntasComRespostasFlow: StateFlow<List<PerguntaComRespostas>> = _perguntasComRespostasFlow

    suspend fun getPerguntasComRespostas(cursoId: Long) {
        // Obtenha as perguntas e respostas do DAO
        viewModelScope.launch(Dispatchers.IO) {
            val perguntas = perguntasQuestionarioDao.getPerguntasByCursoId(cursoId)
            val respostas = respostasQuestionarioDao.getRespostasPorCurso(cursoId)

            // Combine as perguntas e respostas
            val perguntasComRespostas = perguntas.map { pergunta ->
                val respostasRelacionadas =
                    respostas.filter { it.id_pergunta == pergunta.id_pergunta }
                PerguntaComRespostas(pergunta, respostasRelacionadas)
            }

            // Atualize o StateFlow
            _perguntasComRespostasFlow.emit(perguntasComRespostas)
        }
    }

    suspend fun getNivelamentoByPersonIdAndQuizId(personId: Long, quizId: Long): Flow<RespostasUsuarioQuestionario?> {
        return respostasUsuarioQuestionarioDao.getNivelamentoByPersonIdAndQuizId(personId, quizId)
    }

    suspend fun insertEntity(respostasUsuarioQuestionario: RespostasUsuarioQuestionario) {
        respostasUsuarioQuestionarioDao.insertRespostaUsuario(respostasUsuarioQuestionario)
    }

    fun updateEntity(copy: RespostasUsuarioQuestionario) {
        viewModelScope.launch(Dispatchers.IO) {
            respostasUsuarioQuestionarioDao.updateRespostaUsuario(copy)
        }
    }

    suspend fun verificarSePerguntasConcluidas(todasPerguntasIds: List<Long>): Boolean {
        val respostas = withContext(Dispatchers.IO) {
            respostasUsuarioQuestionarioDao.getRespostasByPerguntasIds(todasPerguntasIds)
        }

        Log.d("NivelamentoViewModel", "Respostas: $respostas")
        val todasConcluidas = respostas.size == todasPerguntasIds.size
        Log.d("NivelamentoViewModel", "Todas concluídas: $todasConcluidas")

        return todasConcluidas
    }

    fun finalizarNivelamento(todasPerguntasIds: List<Long>, cursoId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val respostas = respostasUsuarioQuestionarioDao.getRespostasByPerguntasIds2(todasPerguntasIds)

            if (respostas.size == todasPerguntasIds.size) {
                // Atualize o banco de dados
                dataRepository.enviarRespostasNivelamento(respostas, cursoId)
            }
        }
    }


}