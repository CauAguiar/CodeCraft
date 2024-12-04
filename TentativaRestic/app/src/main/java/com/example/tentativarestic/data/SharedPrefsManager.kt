package com.example.tentativarestic.data;

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USER_ID = "user_id"
    }

    // Função para salvar o ID do usuário
    fun saveUserId(userId: Long) {
        sharedPreferences.edit().apply {
            putLong(KEY_USER_ID, userId)
            apply() // Salva de forma assíncrona
        }
    }

    // Função para recuperar o ID do usuário
    fun getUserId(): Long {
        return sharedPreferences.getLong(KEY_USER_ID, -1) // Retorna -1 caso não tenha sido salvo
    }

    // Função para verificar se o usuário já tem ID salvo
    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.contains(KEY_USER_ID)
    }

    // Função para limpar os dados (ex: logout)
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
