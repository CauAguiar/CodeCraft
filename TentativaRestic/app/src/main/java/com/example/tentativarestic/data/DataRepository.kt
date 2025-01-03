package com.example.tentativarestic.data

import android.util.Log
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
            val response = api.getCursos()
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
        try {
            // Passo 1: Buscar as unidades pela API
            val unidadesResponse = api.getUnidadesByLanguage(languageName)
            val modulosResponse = api.getModulosByUnidadeIds(unidadesResponse.map { it.id })

            // Log para verificar as respostas da API
            Log.d("SyncData", "Unidades recebidas da API: $unidadesResponse")
            Log.d("SyncData", "Módulos recebidos da API: $modulosResponse")

            // Passo 2: Salvar ou atualizar as unidades no banco de dados
            if (unidadesResponse.isNotEmpty()) {
                unidadeDao.insertUnidades(unidadesResponse)
                Log.d("SyncData", "Unidades inseridas/atualizadas no banco de dados.")
            } else {
                Log.d("SyncData", "Nenhuma unidade encontrada para o curso.")
            }

            val unidadesSalvas = unidadeDao.getUnidadesByIds(unidadesResponse.map { it.id })
            Log.d("SyncData", "Unidades salvas no banco: $unidadesSalvas")

            if (modulosResponse.isNotEmpty()) {
                moduloDao.insertModulos(modulosResponse)
                Log.d("SyncData", "Módulos inseridos/atualizados no banco de dados.")
            }

            // Passo 3: Salvar ou atualizar os módulos no banco de dados
            if (modulosResponse.isNotEmpty()) {
                moduloDao.insertModulos(modulosResponse)
                Log.d("SyncData", "Módulos inseridos/atualizados no banco de dados.")
            } else {
                Log.d("SyncData", "Nenhum módulo encontrado para as unidades.")
            }
        } catch (e: Exception) {
            Log.e("SyncData", "Erro ao sincronizar dados: ${e.message}")
        }
    }


    suspend fun getUnidadesByCursoNome(cursoNome: String): List<Unidade> {
        val unidades = unidadeDao.getUnidadesByCursoNome(cursoNome)
        Log.d("DataRepository", "Unidades retornadas: $unidades")
        return unidades
    }
}
