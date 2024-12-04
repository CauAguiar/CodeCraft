package com.example.tentativarestic.data

import com.example.tentativarestic.models.PersonResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("api/person/register")
    suspend fun registerPerson(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("phoneNumber") phoneNumber: String
    ): Response<PersonResponse>

    @POST("users/confirm")
    suspend fun confirmUser(
        @Query("telefone") telefone: String,
        @Query("codigo") codigo: String
    ): Response<String>

    @POST("api/person/checkExists")
    suspend fun checkIfExists(
        @Body params: Map<String, String>  // Usando um mapa para enviar os parâmetros no corpo
    ): Response<Map<String, Boolean>>

    @POST("api/person/register")
    suspend fun registerPerson(@Body params: Map<String, String>): Response<Long> // Retorna apenas o ID
}


