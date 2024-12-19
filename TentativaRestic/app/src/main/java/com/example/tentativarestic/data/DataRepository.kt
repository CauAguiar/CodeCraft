package com.example.tentativarestic.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class DataRepository(private val database: AppDatabase) {

    private val atividadeDao = database.atividadeDao()
    private val cursoDao = database.cursoDao()
    private val moduloDao = database.moduloDao()
    private val personDao = database.personDao()

    // Função para sincronizar atividades
    suspend fun syncAtividades() {
        try {
            val response = RetrofitInstance.api.getAtividades()
            if (response.isSuccessful) {
                response.body()?.let {
                    withContext(Dispatchers.IO) {
                        atividadeDao.insertAtividades(it)
                    }
                }
            }
        } catch (e: HttpException) {
            // Tratar erro de rede
        }
    }

    // Função para sincronizar cursos
    suspend fun syncCursos() {
        try {
            val response = RetrofitInstance.api.getCursos()
            if (response.isSuccessful) {
                response.body()?.let {
                    withContext(Dispatchers.IO) {
                        cursoDao.insertCursos(it)
                    }
                }
            }
        } catch (e: HttpException) {
            // Tratar erro de rede
        }
    }

    // Função para sincronizar módulos
    suspend fun syncModulos() {
        try {
            val response = RetrofitInstance.api.getModulos()
            if (response.isSuccessful) {
                response.body()?.let {
                    withContext(Dispatchers.IO) {
                        moduloDao.insertModulos(it)
                    }
                }
            }
        } catch (e: HttpException) {
            // Tratar erro de rede
        }
    }

    // Função para sincronizar todas as entidades
    suspend fun syncAll() {
        syncAtividades()
        syncCursos()
        syncModulos()
    }
}
