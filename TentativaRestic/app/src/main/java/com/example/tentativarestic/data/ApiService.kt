package com.example.tentativarestic.data

import com.example.tentativarestic.entities.Curso
import com.example.tentativarestic.entities.Modulo
import com.example.tentativarestic.entities.Atividade
import com.example.tentativarestic.entities.Person
import com.example.tentativarestic.entities.Unidade
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

    @GET("path/to/your/api/atividade")
    suspend fun getAtividades(): Response<List<Atividade>>

    @GET("path/to/your/api/curso")
    suspend fun getCursos(): Response<List<Curso>>

    @GET("path/to/your/api/modulo")
    suspend fun getModulos(): Response<List<Modulo>>

    @GET("api/unidade/getByLanguage")
    suspend fun getUnidadesByLanguage(@Query("language") languageName: String): List<Unidade>

    @GET("api/modulo/getByUnidadeIds")
    suspend fun getModulosByUnidadeIds(@Query("unidade_ids") unidadeIds: List<Long>): List<Modulo>
}


