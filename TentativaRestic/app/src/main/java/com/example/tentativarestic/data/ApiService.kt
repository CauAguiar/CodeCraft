package com.example.tentativarestic.data

import com.example.tentativarestic.models.Person
import com.example.tentativarestic.models.PersonResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("api/person/login/email")
    fun loginWithEmail(@Body credentials: Map<String, String>): Call<Boolean>

    @POST("/api/person/getemail")
    fun getPersonByEmail(@Body requestBody: Map<String, String>): Call<Person>

    @POST("api/person/checkExists")
    suspend fun checkIfExists(
        @Body params: Map<String, String>  // Usando um mapa para enviar os parâmetros no corpo
    ): Response<Map<String, Boolean>>

    @POST("api/person/register")
    suspend fun registerPerson(@Body params: Map<String, String>): Response<Long> // Retorna apenas o ID
}


