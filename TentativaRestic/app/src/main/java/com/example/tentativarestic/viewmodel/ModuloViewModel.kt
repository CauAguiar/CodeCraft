package com.example.tentativarestic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tentativarestic.data.AppDatabase
import com.example.tentativarestic.entities.Modulo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ModuloViewModel(private val database: AppDatabase) : ViewModel() {
    private val moduloDao = database.moduloDao()

    val modulos: StateFlow<List<Modulo>> = moduloDao.getAllModulos()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addModulo(modulo: Modulo) {
        viewModelScope.launch {
            moduloDao.insertModulo(modulo)
        }
    }

    fun updateModulo(modulo: Modulo) {
        viewModelScope.launch {
            moduloDao.updateModulo(modulo)
        }
    }

    fun deleteModulo(modulo: Modulo) {
        viewModelScope.launch {
            moduloDao.deleteModulo(modulo)
        }
    }

    fun getModulosByUnidadeId(unidadeId: Long): StateFlow<List<Modulo>> {
        return moduloDao.getModulosByUnidadeId(unidadeId)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }
}
