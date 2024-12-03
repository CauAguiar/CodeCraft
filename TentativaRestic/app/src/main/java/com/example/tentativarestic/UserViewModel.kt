package com.example.app.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tentativarestic.data.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel : ViewModel() {
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
