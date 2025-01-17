package com.example.tentativarestic.dao

// ExercicioAbertoDao.kt
import androidx.room.*
import com.example.tentativarestic.entities.ExercicioAberto
import kotlinx.coroutines.flow.Flow

@Dao
interface ExercicioAbertoDao {
    @Query("SELECT * FROM exercicio_aberto")
    fun getAllExercicios(): Flow<List<ExercicioAberto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercicio(exercicio: ExercicioAberto)

    @Update
    suspend fun updateExercicio(exercicio: ExercicioAberto)

    @Delete
    suspend fun deleteExercicio(exercicio: ExercicioAberto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercicios(exercicios: List<ExercicioAberto>)

    @Query("SELECT * FROM exercicio_aberto WHERE id = :atividade_especifica_id")
    fun getExercicioById(atividade_especifica_id: Long): Flow<ExercicioAberto>
}
