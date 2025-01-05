package com.example.tentativarestic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tentativarestic.data.AppDatabase
import com.example.tentativarestic.entities.ExercicioAberto
import com.example.tentativarestic.entities.Projeto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProjetoViewModel(private val database: AppDatabase) : ViewModel() {
    private val projetoDao = database.projetoDao()

    val projetos: StateFlow<List<Projeto>> = projetoDao.getAllProjetos()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addProjeto(projeto: Projeto) {
        viewModelScope.launch {
            projetoDao.insertProjeto(projeto)
        }
    }

    fun updateProjeto(projeto: Projeto) {
        viewModelScope.launch {
            projetoDao.updateProjeto(projeto)
        }
    }

    fun deleteProjeto(projeto: Projeto) {
        viewModelScope.launch {
            projetoDao.deleteProjeto(projeto)
        }
    }

    suspend fun getProjetoById(atividade_especifica_id: Long): Flow<Projeto> {
        return projetoDao.getProjetoById(atividade_especifica_id)
    }
}