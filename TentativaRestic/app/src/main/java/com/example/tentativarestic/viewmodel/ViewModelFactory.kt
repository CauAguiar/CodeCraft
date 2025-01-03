package com.example.tentativarestic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tentativarestic.data.AppDatabase
import com.example.tentativarestic.data.DataRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val database: AppDatabase, private val dataRepository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AtividadeViewModel::class.java) -> {
                AtividadeViewModel(database, dataRepository) as T
            }
            modelClass.isAssignableFrom(CursoViewModel::class.java) -> {
                CursoViewModel(database, dataRepository) as T
            }
            modelClass.isAssignableFrom(ExercicioAbertoViewModel::class.java) -> {
                ExercicioAbertoViewModel(database) as T
            }
            modelClass.isAssignableFrom(ModuloViewModel::class.java) -> {
                ModuloViewModel(database, dataRepository) as T
            }
            modelClass.isAssignableFrom(PersonViewModel::class.java) -> {
                PersonViewModel(database) as T
            }
            modelClass.isAssignableFrom(QuizViewModel::class.java) -> {
                QuizViewModel(database) as T
            }
            modelClass.isAssignableFrom(UnidadeViewModel::class.java) -> {
                UnidadeViewModel(database, dataRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
