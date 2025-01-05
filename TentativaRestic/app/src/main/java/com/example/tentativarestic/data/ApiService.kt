package com.example.tentativarestic.data

import com.example.tentativarestic.entities.Curso
import com.example.tentativarestic.entities.Modulo
import com.example.tentativarestic.entities.Atividade
import com.example.tentativarestic.entities.ExercicioAberto
import com.example.tentativarestic.entities.Person
import com.example.tentativarestic.entities.Projeto
import com.example.tentativarestic.entities.Quiz
import com.example.tentativarestic.entities.Unidade
import com.example.tentativarestic.entities.Video
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("api/person/login/email")
    fun loginWithEmail(@Body credentials: Map<String, String>): Call<Boolean>

    @POST("/api/person/getemail")
    fun getPersonByEmail(@Body requestBody: Map<String, String>): Call<com.example.tentativarestic.models.Person>
//
    @POST("api/person/checkExists")
    suspend fun checkIfExists(
        @Body params: Map<String, String>  // Usando um mapa para enviar os parâmetros no corpo
    ): Response<Map<String, Boolean>>
//
//    @POST("api/unidade/getAll")
//    suspend fun getUnidadesByCurso(@Body requestBody: Map<String, String>): Response<List<Unidade>>
//
//    @POST("/api/atividades/processar")
//    fun processarAtividades(@Body atividades: List<Atividade>): Call<ResultadoAtividades>
//
    @POST("api/person/register")
    suspend fun registerPerson(@Body params: Map<String, String>): Response<Long> // Retorna apenas o ID

    @GET("api/atividade/getByModuloId")
    suspend fun getAtividades(@Query("moduloId") moduloId: Long): Response<List<Atividade>>

    @GET("api/curso/getAll")
    suspend fun getCursos(): Response<List<Curso>>

    @GET("path/to/your/api/modulo")
    suspend fun getModulos(): Response<List<Modulo>>

    @GET("api/unidade/getByLanguage")
    suspend fun getUnidadesByLanguage(@Query("language") languageName: String): List<Unidade>

    @GET("api/modulo/getByUnidadeIds")
    suspend fun getModulosByUnidadeIds(@Query("unidade_ids") unidadeIds: List<Long>): List<Modulo>

    @GET("api/quiz/getByModuloId")
    suspend fun getQuizzes(@Query("moduloId") moduloId: Long): Response<List<Quiz>>

    @GET("api/video/getByModuloId")
    suspend fun getVideos(@Query("moduloId") moduloId: Long): Response<List<Video>>

    @GET("api/exercicioaberto/getByModuloId")
    suspend fun getExerciciosAberto(@Query("moduloId") moduloId: Long): Response<List<ExercicioAberto>>

    @GET("api/projeto/getByModuloId")
    suspend fun getProjetos(@Query("moduloId") moduloId: Long): Response<List<Projeto>>


   // @GET("modulos/{moduloId}/licoes")
   // suspend fun getLicoes(@Path("moduloId") moduloId: Long): Response<List<ApiLicao>>

}


