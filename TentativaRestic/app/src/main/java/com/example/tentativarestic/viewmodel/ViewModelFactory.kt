package com.example.tentativarestic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tentativarestic.data.AppDatabase
import java.lang.IllegalArgumentException

class ViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AtividadeViewModel::class.java) -> {
                AtividadeViewModel(database) as T
            }
            modelClass.isAssignableFrom(CursoViewModel::class.java) -> {
                CursoViewModel(database) as T
            }
            modelClass.isAssignableFrom(ExercicioAbertoViewModel::class.java) -> {
                ExercicioAbertoViewModel(database) as T
            }
            modelClass.isAssignableFrom(ModuloViewModel::class.java) -> {
                ModuloViewModel(database) as T
            }
            modelClass.isAssignableFrom(PersonViewModel::class.java) -> {
                PersonViewModel(database) as T
            }
            modelClass.isAssignableFrom(QuizViewModel::class.java) -> {
                QuizViewModel(database) as T
            }
            modelClass.isAssignableFrom(UnidadeViewModel::class.java) -> {
                UnidadeViewModel(database) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
