package com.example.tentativarestic.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitInstance {


        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Isso vai mostrar as requisições e respostas completas
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Adiciona o interceptor para logar as requisições
            .build()

        val api: ApiService = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")  // Altere para 10.0.2.2 para o emulador
            .client(client) // Adiciona o cliente com o interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

}
