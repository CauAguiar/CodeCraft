package com.example.tentativarestic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tentativarestic.data.AppDatabase
import com.example.tentativarestic.data.DataRepository
import com.example.tentativarestic.entities.Curso
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CursoViewModel(private val database: AppDatabase, private val dataRepository: DataRepository) : ViewModel() {
    private val cursoDao = database.cursoDao()

    init {
        viewModelScope.launch {
            syncCursos()
        }
    }

    //private val _syncStatus = MutableLiveData<Boolean>()
   // val syncStatus: LiveData<Boolean> = _syncStatus

    // Função para sincronizar as unidades e módulos com o banco
    suspend fun syncUnidadesAndModulos(languageName: String) {
        try {
            dataRepository.syncUnidadesAndModulos(languageName)
            // _syncStatus.postValue(true) // Marque a sincronização como concluída
        } catch (e: Exception) {
            // _syncStatus.postValue(false) // Caso haja erro
            throw e // Propaga o erro, se necessário
        }
    }


    suspend fun syncCursos() {
            try {
                dataRepository.syncCursos()
                //_syncStatus.postValue(true) // Marque a sincronização como concluída
            } catch (e: Exception) {
                //_syncStatus.postValue(false) // Caso haja erro
            }
    }

    val cursos: StateFlow<List<Curso>> = cursoDao.getAllCursos()
        .distinctUntilChanged() // Garante que só emite valores diferentes do último
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addCurso(curso: Curso) {
        viewModelScope.launch {
            cursoDao.insertCurso(curso)
        }
    }

    fun updateCurso(curso: Curso) {
        viewModelScope.launch {
            cursoDao.updateCurso(curso)
        }
    }

    fun deleteCurso(curso: Curso) {
        viewModelScope.launch {
            cursoDao.deleteCurso(curso)
        }
    }

    suspend fun syncPersonCurso(cursoId: Long, personId: Long) {
        try{
            dataRepository.syncPersonCurso(cursoId, personId)
        } catch (e: Exception) {
            throw e
        }
    }
}
