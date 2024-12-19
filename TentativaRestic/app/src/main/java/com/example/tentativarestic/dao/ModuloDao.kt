package com.example.tentativarestic.dao

// ModuloDao.kt
import androidx.room.*
import com.example.tentativarestic.entities.Modulo
import kotlinx.coroutines.flow.Flow

@Dao
interface ModuloDao {
    @Query("SELECT * FROM modulo")
    fun getAllModulos(): Flow<List<Modulo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModulo(modulo: Modulo)

    @Update
    suspend fun updateModulo(modulo: Modulo)

    @Delete
    suspend fun deleteModulo(modulo: Modulo)
}
