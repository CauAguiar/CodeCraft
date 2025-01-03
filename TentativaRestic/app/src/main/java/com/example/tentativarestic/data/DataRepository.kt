package com.example.tentativarestic.data

import android.util.Log
import com.example.tentativarestic.data.RetrofitInstance.api
import com.example.tentativarestic.entities.Quiz
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
    private val quizDao = database.quizDao()
    private val videoDao = database.videoDao()
    private val exercicioAbertoDao = database.exercicioAbertoDao()
    //private val licaoDao = database.licaoDao()

    // Função para sincronizar atividades
    suspend fun syncAtividadesEEspecificas(moduloId: Long) {
        // Etapa 1: Obter as atividades da API
        val response = api.getAtividades(moduloId)
        if (response.isSuccessful) {
            response.body()?.let {
                withContext(Dispatchers.IO) {
                    atividadeDao.insertAtividades(it)
                }
            }
        }

        // Sincronizando quizzes
        syncQuizzes(moduloId)

        // Sincronizando vídeos
        syncVideos(moduloId)

        // Sincronizando exercícios abertos
        syncExerciciosAberto(moduloId)

        // Sincronizando lições
        //syncLicoes(moduloId)
    }

    suspend fun syncQuizzes(moduloId: Long) {
        // Etapa 1: Obter os quizzes da API
        val responseQuiz = api.getQuizzes(moduloId)
        if (responseQuiz.isSuccessful) {
            responseQuiz.body()?.let { quizzes ->
                withContext(Dispatchers.IO) {
                    // Etapa 2: Inserir os quizzes na tabela 'quiz'
                    quizDao.insertQuizzes(quizzes)
                }
            }
        }
    }

    suspend fun syncVideos(moduloId: Long) {
        // Etapa 1: Obter os videos da API
        val responseVideo = api.getVideos(moduloId)
        if (responseVideo.isSuccessful) {
            responseVideo.body()?.let { videos ->
                withContext(Dispatchers.IO) {
                    // Etapa 2: Inserir os videos na tabela 'video'
                    videoDao.insertVideos(videos)
                }
            }
        }
    }

    suspend fun syncExerciciosAberto(moduloId: Long) {
        // Etapa 1: Obter os exercícios abertos da API
        val responseExercicioAberto = api.getExerciciosAberto(moduloId)
        if (responseExercicioAberto.isSuccessful) {
            responseExercicioAberto.body()?.let { exerciciosAberto ->
                withContext(Dispatchers.IO) {
                    // Etapa 2: Inserir os exercícios abertos na tabela 'exercicio_aberto'
                    exercicioAbertoDao.insertExercicios(exerciciosAberto)
                }
            }
        }
    }

    suspend fun syncLicoes(moduloId: Long) {
        // Etapa 1: Obter as lições da API
//        val responseLicao = api.getLicoes(moduloId)
//        if (responseLicao.isSuccessful) {
//            responseLicao.body()?.let { licoes ->
//                withContext(Dispatchers.IO) {
//                    // Etapa 2: Inserir as lições na tabela 'licao'
//                    licaoDao.insertLicoes(licoes)
//                }
//            }
//        }
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
        //syncAtividades()
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
