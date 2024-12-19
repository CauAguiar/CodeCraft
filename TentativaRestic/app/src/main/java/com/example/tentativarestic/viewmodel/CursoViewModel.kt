package com.example.tentativarestic.viewmodel

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

    // Função para sincronizar as unidades e módulos com o banco
    fun syncUnidadesAndModulos(languageName: String) {
        viewModelScope.launch {
            try {
                dataRepository.syncUnidadesAndModulos(languageName)
                // Atualize a UI ou estado aqui se necessário
            } catch (e: Exception) {
                // Trate erros de sincronização
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
