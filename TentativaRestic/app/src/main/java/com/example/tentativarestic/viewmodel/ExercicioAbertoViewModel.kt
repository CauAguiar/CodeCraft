package com.example.tentativarestic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tentativarestic.data.AppDatabase
import com.example.tentativarestic.entities.ExercicioAberto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExercicioAbertoViewModel(private val database: AppDatabase) : ViewModel() {
    private val exercicioAbertoDao = database.exercicioAbertoDao()

    val exercicios: StateFlow<List<ExercicioAberto>> = exercicioAbertoDao.getAllExercicios()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addExercicio(exercicio: ExercicioAberto) {
        viewModelScope.launch {
            exercicioAbertoDao.insertExercicio(exercicio)
        }
    }

    fun updateExercicio(exercicio: ExercicioAberto) {
        viewModelScope.launch {
            exercicioAbertoDao.updateExercicio(exercicio)
        }
    }

    fun deleteExercicio(exercicio: ExercicioAberto) {
        viewModelScope.launch {
            exercicioAbertoDao.deleteExercicio(exercicio)
        }
    }

    suspend fun getExercicioById(atividade_especifica_id: Long): Flow<ExercicioAberto> {
        return exercicioAbertoDao.getExercicioById(atividade_especifica_id)
    }
}
