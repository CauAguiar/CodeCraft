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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CursoViewModel(private val database: AppDatabase, private val dataRepository: DataRepository) : ViewModel() {
    private val cursoDao = database.cursoDao()

    //private val _syncStatus = MutableLiveData<Boolean>()
   // val syncStatus: LiveData<Boolean> = _syncStatus

    // Função para sincronizar as unidades e módulos com o banco
    fun syncUnidadesAndModulos(languageName: String) {
        viewModelScope.launch {
            try {
                dataRepository.syncUnidadesAndModulos(languageName)
               // _syncStatus.postValue(true) // Marque a sincronização como concluída
            } catch (e: Exception) {
                //_syncStatus.postValue(false) // Caso haja erro
            }
        }
    }

    fun syncCursos() {
        viewModelScope.launch {
            try {
                dataRepository.syncCursos()
                //_syncStatus.postValue(true) // Marque a sincronização como concluída
            } catch (e: Exception) {
                //_syncStatus.postValue(false) // Caso haja erro
            }
        }
    }

    val cursos: StateFlow<List<Curso>> = cursoDao.getAllCursos()
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
}
