package com.example.app.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tentativarestic.data.RetrofitInstance
import com.example.tentativarestic.data.SharedPrefsManager
import com.example.tentativarestic.models.Atividade
import com.example.tentativarestic.models.Person
import com.example.tentativarestic.models.PersonResponse
import com.example.tentativarestic.models.ResultadoAtividades
import com.example.tentativarestic.models.Unidade
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPrefsManager = SharedPrefsManager(application.applicationContext)

    private val _userId = mutableStateOf<Long?>(null)
    val userId: State<Long?> = _userId

    private val _unidades = MutableLiveData<List<Unidade>>()
    val unidades: LiveData<List<Unidade>> = _unidades

    fun processarAtividades(atividades: List<Atividade>) {
        val call = RetrofitInstance.api.processarAtividades(atividades)

        call.enqueue(object : Callback<ResultadoAtividades> {
            override fun onResponse(
                call: Call<ResultadoAtividades>,
                response: Response<ResultadoAtividades>
            ) {
                if (response.isSuccessful) {
                    val resultado = response.body()
                    resultado?.let {
                        // Processar os resultados
                        println("Quizzes: ${it.quiz}")
                        println("Projetos: ${it.projeto}")
                        println("Exercícios Abertos: ${it.exercicio_aberto}")
                        println("Vídeos: ${it.video}")
                        sharedPrefsManager.saveResultadoAtividades(it)
                    }
                } else {
                    println("Erro: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResultadoAtividades>, t: Throwable) {
                println("Falha na requisição: ${t.message}")
            }
        })
    }

    fun getUnidadesByCurso(nomeCurso: String) {
        // Exemplo de uso do Retrofit com uma corrotina
        viewModelScope.launch {
            try {
                // Faz a requisição à API passando o nome do curso
                val response = RetrofitInstance.api.getUnidadesByCurso(mapOf("nomeCurso" to nomeCurso))

                if (response.isSuccessful) {
                    val unidadesRecebidas = response.body() ?: emptyList()

                    // Atualiza o estado das unidades no ViewModel
                    _unidades.value = unidadesRecebidas

                    // Agora salva as unidades no SharedPreferences
                    sharedPrefsManager.saveUnidades(unidadesRecebidas)

                } else {
                    // Lidar com falha na requisição (caso necessário)
                    _unidades.value = emptyList()
                }
            } catch (e: Exception) {
                // Lidar com erros de rede ou outros erros inesperados
                _unidades.value = emptyList()
            }
        }
    }



    fun loginWithEmail(email: String, password: String, onResult: (Boolean) -> Unit) {
        val credentials = mapOf("email" to email, "password" to password)

        // Faz a chamada à API
        RetrofitInstance.api.loginWithEmail(credentials).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    val success = response.body() ?: false

                    if(success) {
                        sharedPrefsManager.saveUserEmail(email)
                        fetchPersonByEmail(email)
                    }
                    onResult(success)
                } else {
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                // Em caso de erro na requisição
                onResult(false)
            }
        })
    }

    fun fetchPersonByEmail(email: String) {
        val requestBody = mapOf("email" to email)

        val call = RetrofitInstance.api.getPersonByEmail(requestBody)

        call.enqueue(object : retrofit2.Callback<Person> {
            override fun onResponse(call: Call<Person>, response: retrofit2.Response<Person>) {
                if (response.isSuccessful) {
                    val person = response.body()
                    // Use o objeto person conforme necessário
                    println("Pessoa encontrada: $person")
                    sharedPrefsManager.saveUserId(person?.id ?: -1)
                    sharedPrefsManager.saveUserName(person?.name ?: "")
                    sharedPrefsManager.saveUserPhone(person?.telefone ?: "")
                    sharedPrefsManager.saveUserBirthdate(person?.dataNascimento ?: "")
                    sharedPrefsManager.saveUserEmail(person?.email ?: "")
                    sharedPrefsManager.saveUserSecondName(person?.secondName ?: "")
                    sharedPrefsManager.saveUserAge(person?.age ?: 0)
                    sharedPrefsManager.saveUserGender(person?.gender ?: "")
                    sharedPrefsManager.saveUserFacebookId(person?.facebookId ?: "")
                    sharedPrefsManager.saveUserGoogleId(person?.googleId ?: "")
                    sharedPrefsManager.saveUserConfirmationCode(person?.codigoConfirmacao ?: "")
                } else {
                    println("Erro ao obter a pessoa.")
                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                println("Falha na requisição: ${t.message}")
            }
        })
    }


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
                    sharedPrefsManager.saveUserName(name)
                    sharedPrefsManager.saveUserPhone(telefone)
                    sharedPrefsManager.saveUserBirthdate(dataNascimento)
                    sharedPrefsManager.saveUserEmail(email)
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
