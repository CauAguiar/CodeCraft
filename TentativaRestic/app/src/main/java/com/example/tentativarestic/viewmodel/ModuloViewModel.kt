package com.example.tentativarestic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tentativarestic.data.AppDatabase
import com.example.tentativarestic.entities.Modulo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import com.example.tentativarestic.data.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ModuloViewModel(private val database: AppDatabase, private val dataRepository: DataRepository) : ViewModel() {
    private val moduloDao = database.moduloDao()

    private val _modulos = MutableStateFlow<List<Modulo>>(emptyList())
    val modulosT: StateFlow<List<Modulo>> get() = _modulos

    fun carregarModulos(unidadeId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedModulos = moduloDao.getModulosByUnidadeId(unidadeId)
            _modulos.value = fetchedModulos
        }
    }

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

//    private val _modulosState: MutableStateFlow<List<Modulo>> = MutableStateFlow(emptyList())
//    val modulosState: StateFlow<List<Modulo>> get() = _modulosState
//
//    fun getModulosByUnidadeId(unidadeId: Long): StateFlow<List<Modulo>> {
//        viewModelScope.launch {
//            moduloDao.getModulosByUnidadeId(unidadeId)
//                .distinctUntilChanged() // Evita emissões redundantes
//                .collect { modulos ->
//                    _modulosState.value = modulos
//                }
//        }
//        return modulosState
//    }

    suspend fun getModulosByUnidadeId(unidadeId: Long): List<Modulo> {
        return moduloDao.getModulosByUnidadeId(unidadeId)
    }



    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    suspend fun syncAtividadesEEspecificas(moduloId: Long) {
        try {
            _isLoading.value = true
            dataRepository.syncAtividadesEEspecificas(moduloId)
            // Sincronizando quizzes
            //dataRepository.syncQuizzes(moduloId)

            // Sincronizando vídeos
            //dataRepository.syncVideos(moduloId)

            // Sincronizando exercícios abertos
            //dataRepository.syncExerciciosAberto(moduloId)
        } catch (e: Exception) {
            throw e
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun syncQuizzes(moduloId: Long) {
        try {
            dataRepository.syncQuizzes(moduloId)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun syncProjetos(moduloId: Long) {
        try {
            dataRepository.syncProjetos(moduloId)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun syncVideos(moduloId: Long) {
        try {
            dataRepository.syncVideos(moduloId)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun syncExerciciosAberto(moduloId: Long) {
        try {
            dataRepository.syncExerciciosAberto(moduloId)
        } catch (e: Exception) {
            throw e
        }
    }
}
