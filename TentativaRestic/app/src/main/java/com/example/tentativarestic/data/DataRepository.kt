package com.example.tentativarestic.data

import com.example.tentativarestic.data.RetrofitInstance.api
import com.example.tentativarestic.entities.Unidade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class DataRepository(private val database: AppDatabase) {

    private val atividadeDao = database.atividadeDao()
    private val cursoDao = database.cursoDao()
    private val moduloDao = database.moduloDao()
    private val personDao = database.personDao()
    private val unidadeDao = database.unidadeDao()

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

    suspend fun syncUnidadesAndModulos(languageName: String) {
        // Passo 1: Buscar as unidades pela API
        val unidadesResponse = api.getUnidadesByLanguage(languageName)
        val modulosResponse = api.getModulosByUnidadeIds(unidadesResponse.map { it.id })

        // Passo 2: Salvar ou atualizar as unidades no banco de dados
        unidadeDao.insertUnidades(unidadesResponse)
        moduloDao.insertModulos(modulosResponse)
    }

    suspend fun getUnidadesByCursoNome(cursoNome: String): List<Unidade> {
        return unidadeDao.getUnidadesByCursoNome(cursoNome)
    }
}
