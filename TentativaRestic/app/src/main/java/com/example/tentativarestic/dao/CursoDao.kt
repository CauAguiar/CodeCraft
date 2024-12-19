package com.example.tentativarestic.dao

// CursoDao.kt
import androidx.room.*
import com.example.tentativarestic.entities.Curso
import kotlinx.coroutines.flow.Flow

@Dao
interface CursoDao {
    @Query("SELECT * FROM curso")
    fun getAllCursos(): Flow<List<Curso>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurso(curso: Curso)

    @Update
    suspend fun updateCurso(curso: Curso)

    @Delete
    suspend fun deleteCurso(curso: Curso)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCursos(cursos: List<Curso>)
}
