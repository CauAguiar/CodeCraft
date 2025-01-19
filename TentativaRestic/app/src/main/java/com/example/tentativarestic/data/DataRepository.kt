package com.example.tentativarestic.data

import android.util.Log
import com.example.tentativarestic.data.RetrofitInstance.api
import com.example.tentativarestic.entities.PerguntasQuestionario
import com.example.tentativarestic.entities.PersonCurso
import com.example.tentativarestic.entities.RespostasQuestionario
import com.example.tentativarestic.entities.RespostasUsuarioQuestionario
import com.example.tentativarestic.entities.Unidade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
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
    private val projetoDao = database.projetoDao()
    private val personQuizDao = database.personQuizDao()
    private val perguntasQuestionarioDao = database.perguntasQuestionarioDao()
    private val respostasQuestionarioDao = database.respostasQuestionarioDao()
    private val respostasUsuarioQuestionarioDao = database.respostasUsuarioQuestionarioDao()
    private val personCursoDao = database.personCursoDao()
    //private val licaoDao = database.licaoDao()

    // Função para sincronizar atividades
    suspend fun syncAtividadesEEspecificas(moduloId: Long) {
        try {
            // Etapa 1: Obter as atividades da API
            val response = api.getAtividades(moduloId)
            if (response.isSuccessful) {
                response.body()?.let { atividades ->
                    // Inserir no Room em uma thread de IO
                    withContext(Dispatchers.IO) {
                        atividadeDao.insertAtividades(atividades)
                    }
                }
            } else {
                Log.e("Sync", "Falha na resposta da API: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            // Trata qualquer exceção durante a chamada da API
            Log.e("Sync", "Erro ao sincronizar atividades: ${e.message}")
        }
    }

    suspend fun syncProjetos(moduloId: Long) {
        try {
            val response = api.getProjetos(moduloId)
            if (response.isSuccessful) {
                response.body()?.let {
                    withContext(Dispatchers.IO) {
                        projetoDao.insertProjetos(it)
                    }
                }
            }
        } catch (e: HttpException) {
            // Tratar erro de rede
        }
    }


//        // Sincronizando quizzes
//        syncQuizzes(moduloId)
//
//        // Sincronizando vídeos
//        syncVideos(moduloId)
//
//        // Sincronizando exercícios abertos
//        syncExerciciosAberto(moduloId)

        // Sincronizando lições
        //syncLicoes(moduloId)

    // Função para sincronizar quizzes
    suspend fun syncQuizzes(moduloId: Long) {
        try {
            // Etapa 1: Obter os quizzes da API
            val responseQuiz = api.getQuizzes(moduloId)
            if (responseQuiz.isSuccessful) {
                responseQuiz.body()?.let { quizzes ->
                    withContext(Dispatchers.IO) {
                        // Etapa 2: Inserir os quizzes na tabela 'quiz'
                        quizDao.insertQuizzes(quizzes)
                        Log.d("Sync", "Quizzes sincronizados com sucesso")
                    }
                }
            } else {
                Log.e("Sync", "Erro ao obter quizzes da API: ${responseQuiz.code()}")
            }
        } catch (e: Exception) {
            Log.e("Sync", "Erro ao sincronizar quizzes: ${e.message}")
        }
    }

    // Função para sincronizar vídeos
    suspend fun syncVideos(moduloId: Long) {
        try {
            // Etapa 1: Obter os vídeos da API
            val responseVideo = api.getVideos(moduloId)
            if (responseVideo.isSuccessful) {
                responseVideo.body()?.let { videos ->
                    withContext(Dispatchers.IO) {
                        // Etapa 2: Inserir os vídeos na tabela 'video'
                        videoDao.insertVideos(videos)
                        Log.d("Sync", "Vídeos sincronizados com sucesso")
                    }
                }
            } else {
                Log.e("Sync", "Erro ao obter vídeos da API: ${responseVideo.code()}")
            }
        } catch (e: Exception) {
            Log.e("Sync", "Erro ao sincronizar vídeos: ${e.message}")
        }
    }

    // Função para sincronizar exercícios abertos
    suspend fun syncExerciciosAberto(moduloId: Long) {
        try {
            // Etapa 1: Obter os exercícios abertos da API
            val responseExercicioAberto = api.getExerciciosAberto(moduloId)
            if (responseExercicioAberto.isSuccessful) {
                responseExercicioAberto.body()?.let { exerciciosAberto ->
                    withContext(Dispatchers.IO) {
                        // Etapa 2: Inserir os exercícios abertos na tabela 'exercicio_aberto'
                        exercicioAbertoDao.insertExercicios(exerciciosAberto)
                        Log.d("Sync", "Exercícios abertos sincronizados com sucesso")
                    }
                }
            } else {
                Log.e("Sync", "Erro ao obter exercícios abertos da API: ${responseExercicioAberto.code()}")
            }
        } catch (e: Exception) {
            Log.e("Sync", "Erro ao sincronizar exercícios abertos: ${e.message}")
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

    suspend fun syncPerguntasComRespostas(cursoId: Long) {
        try{
            val response = api.getPerguntasComRespostas(cursoId)
            if (response.isSuccessful) {
                response.body()?.let { jsonList ->
                    val perguntas = mutableListOf<PerguntasQuestionario>()
                    val respostas = mutableListOf<RespostasQuestionario>()

                    jsonList.forEach { json ->
                        val idPergunta = (json["idPergunta"] as Double).toLong()
                        val pergunta = json["pergunta"] as String
                        val idCurso = (json["idCurso"] as Double).toLong()

                        // Adicionar à lista de perguntas
                        perguntas.add(PerguntasQuestionario(idPergunta, pergunta, idCurso))

                        // Processar respostas
                        val respostasJson = json["respostas"] as List<Map<String, Any>>
                        respostasJson.forEach { respostaJson ->
                            val idResposta = (respostaJson["idResposta"] as Double).toLong()
                            val resposta = respostaJson["resposta"] as String
                            val peso = (respostaJson["peso"] as Double).toInt()

                            respostas.add(RespostasQuestionario(idResposta, idPergunta, resposta, peso))
                        }
                    }

                    perguntasQuestionarioDao.insertPerguntas(perguntas)
                    respostasQuestionarioDao.insertRespostas(respostas)

                    // Agora você tem listas separadas de PerguntaQuestionario e RespostaQuestionario
                    println("Perguntas: $perguntas")
                    println("Respostas: $respostas")
                }
            } else {
                println("Erro ao buscar dados: ${response.errorBody()?.string()}")
            }

        } catch (e: Exception) {
            println("Erro ao sincronizar perguntas com respostas: ${e.message}")
        }
    }

    data class RespostasNivelamentoRequest(
        val respostas: List<RespostasUsuarioQuestionario>,
        val cursoId: Long
    )

    suspend fun enviarRespostasNivelamento(respostas: List<RespostasUsuarioQuestionario>, cursoId: Long) {
        // Cria o objeto que será enviado para a API
        val request = RespostasNivelamentoRequest(respostas, cursoId)

        // Enviar respostas e cursoId para a API
        try {
            val response = api.enviarRespostasNivelamento(request)
            Log.d("DataRepository", "Respostas enviadas com sucesso para a API ${request}")

            if (response.isSuccessful) {
                // A resposta foi bem-sucedida, acessa o corpo
                val responseBody = response.body()?.string()
                Log.d("DataRepository", "Resposta da API: $responseBody")
                responseBody?.let {
                    val jsonObject = JSONObject(it)
                    val nivelamento = jsonObject.optString("nivelamento")
                    println("Nivelamento: $nivelamento")  // Isso vai imprimir "Básico"

                    val existingPersonCurso = personCursoDao.getPersonCursoByPersonIdAndCursoId(respostas[0].id_person, cursoId)

                    if (existingPersonCurso != null) {
                        // Registro existe, faça o update
                        personCursoDao.updatePersonCurso(PersonCurso(respostas[0].id_person, cursoId, nivelamento, false, true))
                    } else {
                        // Registro não encontrado, talvez insira um novo
                        personCursoDao.insertPersonCurso(PersonCurso(respostas[0].id_person, cursoId, nivelamento, false, true))
                    }
                }

            } else {
                // A resposta falhou, pega o código e o corpo do erro
                val errorBody = response.errorBody()?.string()
                Log.e("DataRepository", "Erro na API: Código ${response.code()}, Erro: $errorBody")
            }
        } catch (e: Exception) {
            // Tratar exceção
            Log.e("DataRepository", "Erro ao enviar respostas: ${e.message}")
        }
    }

    suspend fun syncPersonCurso(cursoId: Long, personId: Long) {
        try{
            Log.d("DataRepository", "Sincronizando personCurso $personId com curso $cursoId")
            val response = api.getPersonCurso(cursoId, personId)
            Log.d("DataRepository", "Resposta da API: $response")
            if (response.isSuccessful) {
                val responseBody = response.body()

                if (responseBody != null) {
                    when (responseBody) {
                        is PersonCurso -> {
                            // Processar PersonCurso
                            Log.d("DataRepository", "Resposta da API PersonCurso: $responseBody")
                            personCursoDao.insertPersonCurso(responseBody)
                        }
                        is String -> {
                            // Se for uma String, trate a resposta adequadamente
                            Log.d("DataRepository", "Resposta da API é uma String: $responseBody")
                            val personCurso = PersonCurso(personId, cursoId, "", false, true)
                            personCursoDao.insertPersonCurso(personCurso)
                        }
                        else -> {
                            Log.d("DataRepository", "Resposta da API com tipo inesperado: $responseBody")
                        }
                    }
                } else {
                    Log.d("DataRepository", "Resposta da API foi nula")
                }
            }
        } catch (e: Exception) {
            // Tratar exceção
            Log.e("DataRepository", "Erro ao sincronizar personCurso: ${e.message}", e)
        }
    }

}
