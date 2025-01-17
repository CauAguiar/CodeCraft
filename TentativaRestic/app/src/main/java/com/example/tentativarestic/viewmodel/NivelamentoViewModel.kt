package com.example.tentativarestic.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tentativarestic.data.AppDatabase
import com.example.tentativarestic.data.DataRepository

class NivelamentoViewModel(private val database: AppDatabase, private val dataRepository: DataRepository) : ViewModel() {
    private val perguntasQuestionarioDao = database.perguntasQuestionarioDao()
    private val respostasQuestionarioDao = database.respostasQuestionarioDao()
    private val respostasUsuarioQuestionarioDao = database.respostasUsuarioQuestionarioDao()

    suspend fun syncPerguntasComRespostas(cursoId : Long) {
        dataRepository.syncPerguntasComRespostas(cursoId)
    }

}