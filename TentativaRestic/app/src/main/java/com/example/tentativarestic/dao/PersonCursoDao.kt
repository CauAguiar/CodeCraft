package com.example.tentativarestic.dao

// PersonCursoDao.kt
import androidx.room.*
import com.example.tentativarestic.entities.PersonCurso
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonCursoDao {
    @Query("SELECT * FROM person_curso")
    fun getAllPersonCursos(): Flow<List<PersonCurso>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonCurso(personCurso: PersonCurso)

    @Update
    suspend fun updatePersonCurso(personCurso: PersonCurso)

    @Delete
    suspend fun deletePersonCurso(personCurso: PersonCurso)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonCursos(personCursos: List<PersonCurso>)

    @Query("SELECT * FROM person_curso WHERE id_person = :idPerson AND id_curso = :cursoId")
    suspend fun getPersonCursoByPersonIdAndCursoId(idPerson: Long, cursoId: Long): PersonCurso?
}
