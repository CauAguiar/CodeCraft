package com.example.tentativarestic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tentativarestic.entities.PersonQuiz
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonQuizDao {
    @Query("SELECT * FROM person_quiz")
    fun getAllPersonQuiz(): Flow<List<PersonQuiz>>

    @Query("SELECT * FROM person_quiz WHERE person_id = :personId")
    fun getPersonQuizByPersonId(personId: Int): Flow<List<PersonQuiz>>

    @Query("SELECT * FROM person_quiz WHERE quiz_id = :quizId")
    fun getPersonQuizByQuizId(quizId: Int): Flow<List<PersonQuiz>>

    @Query("SELECT * FROM person_quiz WHERE person_id = :personId AND quiz_id = :quizId")
    fun getPersonQuizByPersonIdAndQuizId(personId: Long, quizId: Long): Flow<PersonQuiz?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonQuiz(personQuiz: PersonQuiz)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonQuizzes(personQuizzes: List<PersonQuiz>)

    @Update
    suspend fun updatePersonQuiz(personQuiz: PersonQuiz)
}