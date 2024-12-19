package com.example.tentativarestic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tentativarestic.data.AppDatabase
import com.example.tentativarestic.data.DataRepository
import com.example.tentativarestic.entities.Atividade
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AtividadeViewModel(private val database: AppDatabase, private val repository: DataRepository) : ViewModel() {
//    init {
//        viewModelScope.launch {
//            // Sincroniza os dados ao iniciar o ViewModel
//            repository.syncAtividades()
//        }
//    }

    private val atividadeDao = database.atividadeDao()

    // StateFlow para expor os dados de forma reativa
    val atividades: StateFlow<List<Atividade>> = atividadeDao.getAllAtividades()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addAtividade(atividade: Atividade) {
        viewModelScope.launch {
            atividadeDao.insertAtividade(atividade)
        }
    }

    fun updateAtividade(atividade: Atividade) {
        viewModelScope.launch {
            atividadeDao.updateAtividade(atividade)
        }
    }

    fun deleteAtividade(atividade: Atividade) {
        viewModelScope.launch {
            atividadeDao.deleteAtividade(atividade)
        }
    }
}
