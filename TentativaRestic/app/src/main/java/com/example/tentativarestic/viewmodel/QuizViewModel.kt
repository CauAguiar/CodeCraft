package com.example.tentativarestic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tentativarestic.data.AppDatabase
import com.example.tentativarestic.entities.PersonQuiz
import com.example.tentativarestic.entities.Quiz
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuizViewModel(private val database: AppDatabase) : ViewModel() {
    private val quizDao = database.quizDao()
    private val quizPersonDao = database.personQuizDao()

    val quizzes: StateFlow<List<Quiz>> = quizDao.getAllQuizzes()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addQuiz(quiz: Quiz) {
        viewModelScope.launch {
            quizDao.insertQuiz(quiz)
        }
    }

    fun updateQuiz(quiz: Quiz) {
        viewModelScope.launch {
            quizDao.updateQuiz(quiz)
        }
    }

    fun deleteQuiz(quiz: Quiz) {
        viewModelScope.launch {
            quizDao.deleteQuiz(quiz)
        }
    }

    fun getPersonQuizByPersonIdAndQuizId(personId: Long, quizId: Long): Flow<PersonQuiz?> {


            return quizPersonDao.getPersonQuizByPersonIdAndQuizId(personId, quizId)


    }

    fun insertPersonQuiz(personQuiz: PersonQuiz) {
        viewModelScope.launch {
            quizPersonDao.insertPersonQuiz(personQuiz)
        }
    }

    fun updatePersonQuiz(personQuiz: PersonQuiz) {
        viewModelScope.launch {
            quizPersonDao.updatePersonQuiz(personQuiz)
        }
    }

    suspend fun getQuizById(atividade_especifica_id: Long): Flow<Quiz> {
        return quizDao.getQuizById(atividade_especifica_id)
    }
}
