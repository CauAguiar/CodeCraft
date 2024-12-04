package com.example.app.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tentativarestic.data.RetrofitInstance
import com.example.tentativarestic.data.SharedPrefsManager
import com.example.tentativarestic.models.PersonResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPrefsManager = SharedPrefsManager(application.applicationContext)

    private val _userId = mutableStateOf<Long?>(null)
    val userId: State<Long?> = _userId

    // Função para registrar a pessoa
    fun registerPerson(name: String, email: String, password: String, telefone: String, dataNascimento: String) {
        val params = mapOf(
            "name" to name,
            "email" to email,
            "password" to password,
            "telefone" to telefone,
            "dataNascimento" to dataNascimento
        )

        // Chama o método de cadastro no Retrofit
        viewModelScope.launch {
            val response: Response<Long> = try {
                RetrofitInstance.api.registerPerson(params) // Chama a API
            } catch (e: Exception) {
                Log.e("UserViewModel", "Erro ao registrar: ${e.localizedMessage}")
                return@launch
            }

            if (response.isSuccessful) {
                val id = response.body()
                _userId.value = id // Atualiza o ID na ViewModel
                id?.let {
                    // Salva o ID nas SharedPreferences
                    sharedPrefsManager.saveUserId(it)
                }
                Log.d("UserViewModel", "Usuário registrado com ID: $id")
            } else {
                Log.e("UserViewModel", "Erro ao registrar pessoa: ${response.message()}")
            }
        }
    }

    // Função para verificar se o usuário está logado
    fun checkIfUserIsLoggedIn(): Boolean {
        return sharedPrefsManager.isUserLoggedIn()
    }

    // Função para recuperar o ID do usuário armazenado
    fun getUserIdFromPrefs(): Long {
        return sharedPrefsManager.getUserId()
    }

    // Função para limpar os dados (logout)
    fun logout() {
        sharedPrefsManager.clear()
        _userId.value = null
    }

    // Função para verificar se o email ou telefone já existem
    fun checkIfExists(email: String, telefone: String, onResult: (String) -> Unit) {
        viewModelScope.launch {
            try {
                // Criando o map de parâmetros
                val params = mapOf("email" to email, "telefone" to telefone)

                // Chamando a API passando o Map com os parâmetros
                val response: Response<Map<String, Boolean>> =
                    RetrofitInstance.api.checkIfExists(params)

                if (response.isSuccessful) {
                    val data = response.body()

                    // Verifica os dados e chama o onResult com as mensagens apropriadas
                    when {
                        data?.get("email") == true && data["telefone"] == true -> {
                            onResult("Email e telefone já cadastrados.")
                        }

                        data?.get("email") == true -> {
                            onResult("Email já cadastrado.")
                        }

                        data?.get("telefone") == true -> {
                            onResult("Telefone já cadastrado.")
                        }

                        else -> {
                            onResult("")
                        }
                    }
                } else {
                    Log.e("UserViewModel", "Erro ao verificar a existência: ${response.message()}")
                    onResult("Erro na verificação.")
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Erro ao verificar: ${e.localizedMessage}")
                onResult("Erro na verificação.")
            }
        }
    }
}
