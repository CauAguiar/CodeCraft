package com.example.tentativarestic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tentativarestic.data.AppDatabase
import com.example.tentativarestic.entities.Unidade
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UnidadeViewModel(private val database: AppDatabase) : ViewModel() {
    private val unidadeDao = database.unidadeDao()

    val unidades: StateFlow<List<Unidade>> = unidadeDao.getAllUnidades()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addUnidade(unidade: Unidade) {
        viewModelScope.launch {
            unidadeDao.insertUnidade(unidade)
        }
    }

    fun updateUnidade(unidade: Unidade) {
        viewModelScope.launch {
            unidadeDao.updateUnidade(unidade)
        }
    }

    fun deleteUnidade(unidade: Unidade) {
        viewModelScope.launch {
            unidadeDao.deleteUnidade(unidade)
        }
    }
}
