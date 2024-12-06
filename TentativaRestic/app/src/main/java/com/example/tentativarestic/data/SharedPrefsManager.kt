package com.example.tentativarestic.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.tentativarestic.models.Atividade
import com.example.tentativarestic.models.ExercicioAberto
import com.example.tentativarestic.models.Projeto
import com.example.tentativarestic.models.Quiz
import com.example.tentativarestic.models.ResultadoAtividades
import com.example.tentativarestic.models.Unidade
import com.example.tentativarestic.models.Video
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefsManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_BIRTHDATE = "user_birthdate"
        private const val KEY_USER_PHONE = "user_phone"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_SECOND_NAME = "user_second_name"
        private const val KEY_USER_AGE = "user_age"
        private const val KEY_USER_GENDER = "user_gender"
        private const val KEY_USER_FACEBOOK_ID = "user_facebook_id"
        private const val KEY_USER_GOOGLE_ID = "user_google_id"
        private const val KEY_USER_CONFIRMATION_CODE = "user_confirmation_code"

        // Chaves relacionadas a curso, módulo, unidade e atividade
        private const val KEY_CURSO_ID = "curso_id"
        private const val KEY_CURSO_NOME = "curso_nome"
        private const val KEY_CURSO_DESCRICAO = "curso_descricao"
        private const val KEY_CURSO_NIVEL = "curso_nivel"
        private const val KEY_CURSO_CATEGORIA = "curso_categoria"
        private const val KEY_CURSO_ICONE = "curso_icone"
        private const val KEY_CURSO_DURACAO = "curso_duracao"
        private const val KEY_CURSO_HABILITADO = "curso_habilitado"

        private const val KEY_UNIDADE_ID = "unidade_id"
        private const val KEY_UNIDADE_TITULO = "unidade_titulo"
        private const val KEY_UNIDADE_DESCRICAO = "unidade_descricao"
        private const val KEY_UNIDADE_ORDEM = "unidade_ordem"
        private const val KEY_UNIDADE_DURACAO = "unidade_duracao"
        private const val KEY_UNIDADE_HABILITADA = "unidade_habilitada"
        private const val KEY_UNIDADE_ICONE = "unidade_icone"

        private const val KEY_MODULO_ID = "modulo_id"
        private const val KEY_MODULO_NOME = "modulo_nome"
        private const val KEY_MODULO_ORDEM = "modulo_ordem"
        private const val KEY_MODULO_DURACAO = "modulo_duracao"
        private const val KEY_MODULO_HABILITADO = "modulo_habilitado"
        private const val KEY_ATIVIDADES = "atividades"

        private const val KEY_ATIVIDADE_ID = "atividade_id"
        private const val KEY_ATIVIDADE_NOME = "atividade_nome"
        private const val KEY_ATIVIDADE_TIPO = "atividade_tipo"
        private const val KEY_ATIVIDADE_HABILITADA = "atividade_habilitada"
        private const val KEY_ATIVIDADE_CONCLUIDA = "atividade_concluida"
        private const val KEY_ATIVIDADE_ACERTOS = "atividade_acertos"
        private const val KEY_ATIVIDADE_ERROS = "atividade_erros"

        private const val KEY_UNIDADES = "unidades"

        private const val KEY_RESULTADOATIVIDADES = "resultadoatividades"
    }

    fun saveUnidadeOrdem(ordem: Int) {
        sharedPreferences.edit().apply {
            putInt(KEY_UNIDADE_ORDEM, ordem)
            apply()
        }
    }

    fun getUnidadeOrdem(): Int {
        return sharedPreferences.getInt(KEY_UNIDADE_ORDEM, -1)
    }

    fun saveUnidadeID(unidadeId: Long) {
        sharedPreferences.edit().apply {
            putLong(KEY_UNIDADE_ID, unidadeId)
            apply()
        }
    }

    fun getUnidadeID(): Long {
        return sharedPreferences.getLong(KEY_UNIDADE_ID, -1)
    }

    fun saveResultadoAtividades(resultadoAtividades: ResultadoAtividades) {
        val resultadoAtividadesJson = gson.toJson(resultadoAtividades)
        sharedPreferences.edit().apply {
            putString(KEY_RESULTADOATIVIDADES, resultadoAtividadesJson)
            apply()
        }
    }

    fun saveQuiz(updatedQuiz: Quiz) {
        // Pega o JSON armazenado com o ResultadoAtividades
        val resultadoAtividadesJson = sharedPreferences.getString(KEY_RESULTADOATIVIDADES, null)

        if (resultadoAtividadesJson != null) {
            try {
                // Converte o JSON para o objeto ResultadoAtividades
                val listType = object : TypeToken<ResultadoAtividades>() {}.type
                val resultadoAtividades: ResultadoAtividades = gson.fromJson(resultadoAtividadesJson, listType)

                // Verifica se a lista de quizzes não é nula
                val quizzes = resultadoAtividades.quiz?.toMutableList() ?: mutableListOf()

                // Encontra o índice do quiz com o id correspondente ou -1 se não encontrado
                val quizIndex = quizzes.indexOfFirst { it.id == updatedQuiz.id }

                if (quizIndex != -1) {
                    // Substitui o quiz existente com o novo
                    quizzes[quizIndex] = updatedQuiz
                } else {
                    // Se não encontrar, adiciona o quiz à lista
                    quizzes.add(updatedQuiz)
                }

                // Atualiza o objeto ResultadoAtividades com a lista modificada de quizzes
                val updatedResultadoAtividades = ResultadoAtividades(quiz = quizzes, projeto = resultadoAtividades.projeto, exercicio_aberto = resultadoAtividades.exercicio_aberto, video = resultadoAtividades.video, desconhecido = resultadoAtividades.desconhecido)

                // Converte de volta para JSON
                val updatedJson = gson.toJson(updatedResultadoAtividades)

                // Salva o JSON atualizado no SharedPreferences
                sharedPreferences.edit().apply {
                    putString(KEY_RESULTADOATIVIDADES, updatedJson)
                    apply()
                }

                Log.d("QuizDebug", "Quiz salvo ou atualizado com sucesso. ID: ${updatedQuiz.id}")

            } catch (e: Exception) {
                // Trata possíveis erros durante a deserialização ou manipulação dos dados
                e.printStackTrace() // Ou logar o erro
            }
        } else {
            Log.e("QuizDebug", "ResultadoAtividades não encontrado no SharedPreferences.")
        }
    }


    // função que pega resultadoatividades, pega a lista de Quiz dentro dela e retorna o objeto Quiz com o ID correspondente
    fun getQuizById(quizId: Long): Quiz? {
        val resultadoAtividadesJson = sharedPreferences.getString(KEY_RESULTADOATIVIDADES, null)
        if (resultadoAtividadesJson != null) {
            return try {
                val listType = object : TypeToken<ResultadoAtividades>() {}.type
                val resultadoAtividades: ResultadoAtividades = gson.fromJson(resultadoAtividadesJson, listType)
                resultadoAtividades.quiz?.find { it.id == quizId }
            } catch (e: Exception) {
                e.printStackTrace() // Ou logar o erro usando uma ferramenta apropriada
                null
            }
        }
        return null
    }

    fun getProjetoById(projetoId: Long): Projeto? {
        val resultadoAtividadesJson = sharedPreferences.getString(KEY_RESULTADOATIVIDADES, null)
        if (resultadoAtividadesJson != null) {
            return try {
                val listType = object : TypeToken<ResultadoAtividades>() {}.type
                val resultadoAtividades: ResultadoAtividades = gson.fromJson(resultadoAtividadesJson, listType)
                resultadoAtividades.projeto?.find { it.id == projetoId }
            } catch (e: Exception) {
                e.printStackTrace() // Ou logar o erro usando uma ferramenta apropriada
                null
            }
        }
        return null
    }

    fun getVideoById(videoId: Long): Video? {
        val resultadoAtividadesJson = sharedPreferences.getString(KEY_RESULTADOATIVIDADES, null)
        if (resultadoAtividadesJson != null) {
            return try {
                val listType = object : TypeToken<ResultadoAtividades>() {}.type
                val resultadoAtividades: ResultadoAtividades = gson.fromJson(resultadoAtividadesJson, listType)
                resultadoAtividades.video?.find { it.id == videoId }
            } catch (e: Exception) {
                e.printStackTrace() // Ou logar o erro usando uma ferramenta apropriada
                null
            }
        }
        return null
    }

    fun getExercicioAbertoById(exercicioAbertoId: Long): ExercicioAberto? {
        val resultadoAtividadesJson = sharedPreferences.getString(KEY_RESULTADOATIVIDADES, null)
        if (resultadoAtividadesJson != null) {
            return try {
                val listType = object : TypeToken<ResultadoAtividades>() {}.type
                val resultadoAtividades: ResultadoAtividades = gson.fromJson(resultadoAtividadesJson, listType)
                resultadoAtividades.exercicio_aberto?.find { it.id == exercicioAbertoId }
            } catch (e: Exception) {
                e.printStackTrace() // Ou logar o erro usando uma ferramenta apropriada
                null
            }
        }
        return null
    }

    fun saveAtividadeById(id: Long, atividade: Atividade) {
        var atividades = getAtividades()
        // Cria uma nova lista de atividades para adicionar a atividade com o ID correto
        val updatedAtividades = atividades.toMutableList()

        // Verifica se já existe uma Atividade com o mesmo ID
        val existingAtividadeIndex = updatedAtividades.indexOfFirst { it.id == id }

        if (existingAtividadeIndex != -1) {
            // Se a Atividade com o mesmo ID já existir, substitui a atividade existente
            updatedAtividades[existingAtividadeIndex] = atividade
        } else {
            // Se o ID não existir, adiciona a nova Atividade à lista
            updatedAtividades.add(atividade)
        }

        Log.d("AtividadesDebugY", "Atividades Salva: $atividade")

        // Converte a lista de atividades para JSON e salva no SharedPreferences
        val atividadesJson = gson.toJson(updatedAtividades)
        sharedPreferences.edit().apply {
            putString(KEY_ATIVIDADES, atividadesJson)
            apply()
        }

        Log.d("AtividadesDebug", "Atividade salva ou atualizada com sucesso. ID: $id")
    }



    fun saveAtividades(atividades: List<Atividade>) {
        Log.d("AtividadesDebug", "Salvando atividades: $atividades")
        val atividadesJson = gson.toJson(atividades)
        sharedPreferences.edit().apply {
            putString(KEY_ATIVIDADES, atividadesJson)
            apply()
        }
    }

    fun getAtividades(): List<Atividade> {
        val atividadesJson = sharedPreferences.getString(KEY_ATIVIDADES, null)
        Log.d("AtividadesDebug", "Dados recuperados: $atividadesJson")
        return if (atividadesJson != null) {
            val listType = object : TypeToken<List<Atividade>>() {}.type
            gson.fromJson(atividadesJson, listType)
        } else {
            emptyList()
        }
    }

    // Função para salvar uma lista de Unidades
    fun saveUnidades(unidades: List<Unidade>) {
        val unidadesJson = gson.toJson(unidades)
        sharedPreferences.edit().apply {
            putString(KEY_UNIDADES, unidadesJson)
            apply()
        }
    }

    // Função para recuperar a lista de Unidades
    fun getUnidades(): List<Unidade> {
        val unidadesJson = sharedPreferences.getString(KEY_UNIDADES, null)
        return if (unidadesJson != null) {
            val listType = object : TypeToken<List<Unidade>>() {}.type
            gson.fromJson(unidadesJson, listType)
        } else {
            emptyList() // Retorna uma lista vazia se não houver unidades salvas
        }
    }

    fun saveCursoNome(nome: String) {
        sharedPreferences.edit().apply {
            putString(KEY_CURSO_NOME, nome)
            apply()
        }
    }

    fun getCursoNome(): String? {
        return sharedPreferences.getString(KEY_CURSO_NOME, null)
    }

    // Funções para salvar e recuperar dados de Curso
    fun saveCurso(cursoId: Long, nome: String, descricao: String, nivel: String, categoria: String, icone: String?, duracao: Int, habilitado: Boolean) {
        sharedPreferences.edit().apply {
            putLong(KEY_CURSO_ID, cursoId)
            putString(KEY_CURSO_NOME, nome)
            putString(KEY_CURSO_DESCRICAO, descricao)
            putString(KEY_CURSO_NIVEL, nivel)
            putString(KEY_CURSO_CATEGORIA, categoria)
            putString(KEY_CURSO_ICONE, icone)
            putInt(KEY_CURSO_DURACAO, duracao)
            putBoolean(KEY_CURSO_HABILITADO, habilitado)
            apply()
        }
    }

    fun getCursoId(): Long {
        return sharedPreferences.getLong(KEY_CURSO_ID, -1)
    }

//    // Funções para salvar e recuperar dados de Unidade
//    fun saveUnidade(unidadeId: Long, titulo: String, descricao: String, ordem: Int, duracao: Int, habilitada: Boolean, icone: String?) {
//        sharedPreferences.edit().apply {
//            putLong(KEY_UNIDADE_ID, unidadeId)
//            putString(KEY_UNIDADE_TITULO, titulo)
//            putString(KEY_UNIDADE_DESCRICAO, descricao)
//            putInt(KEY_UNIDADE_ORDEM, ordem)
//            putInt(KEY_UNIDADE_DURACAO, duracao)
//            putBoolean(KEY_UNIDADE_HABILITADA, habilitada)
//            putString(KEY_UNIDADE_ICONE, icone)
//            apply()
//        }
//    }
//
//    fun getUnidadeId(): Long {
//        return sharedPreferences.getLong(KEY_UNIDADE_ID, -1)
//    }

    // Funções para salvar e recuperar dados de Módulo
    fun saveModulo(moduloId: Long, nome: String, ordem: Int, duracao: Int, habilitado: Boolean) {
        sharedPreferences.edit().apply {
            putLong(KEY_MODULO_ID, moduloId)
            putString(KEY_MODULO_NOME, nome)
            putInt(KEY_MODULO_ORDEM, ordem)
            putInt(KEY_MODULO_DURACAO, duracao)
            putBoolean(KEY_MODULO_HABILITADO, habilitado)
            apply()
        }
    }

    fun getModuloId(): Long {
        return sharedPreferences.getLong(KEY_MODULO_ID, -1)
    }

    // Funções para salvar e recuperar dados de Atividade
    fun saveAtividade(atividadeId: Long, nome: String, tipo: String, habilitada: Boolean, concluida: Boolean, acertos: Int?, erros: Int?) {
        sharedPreferences.edit().apply {
            putLong(KEY_ATIVIDADE_ID, atividadeId)
            putString(KEY_ATIVIDADE_NOME, nome)
            putString(KEY_ATIVIDADE_TIPO, tipo)
            putBoolean(KEY_ATIVIDADE_HABILITADA, habilitada)
            putBoolean(KEY_ATIVIDADE_CONCLUIDA, concluida)
            putInt(KEY_ATIVIDADE_ACERTOS, acertos ?: 0)
            putInt(KEY_ATIVIDADE_ERROS, erros ?: 0)
            apply()
        }
    }

    fun getAtividadeId(): Long {
        return sharedPreferences.getLong(KEY_ATIVIDADE_ID, -1)
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

    // Função para salvar o nome do usuário
    fun saveUserName(userName: String) {
        sharedPreferences.edit().apply {
            putString(KEY_USER_NAME, userName)
            apply()
        }
    }

    // Função para recuperar o nome do usuário
    fun getUserName(): String? {
        return sharedPreferences.getString(KEY_USER_NAME, null)
    }

    // Função para salvar o nome secundário do usuário
    fun saveUserSecondName(secondName: String?) {
        sharedPreferences.edit().apply {
            putString(KEY_USER_SECOND_NAME, secondName)
            apply()
        }
    }

    // Função para recuperar o nome secundário do usuário
    fun getUserSecondName(): String? {
        return sharedPreferences.getString(KEY_USER_SECOND_NAME, null)
    }

    // Função para salvar a idade do usuário
    fun saveUserAge(age: Int?) {
        sharedPreferences.edit().apply {
            age?.let { putInt(KEY_USER_AGE, it) }
            apply()
        }
    }

    // Função para recuperar a idade do usuário
    fun getUserAge(): Int? {
        return if (sharedPreferences.contains(KEY_USER_AGE)) {
            sharedPreferences.getInt(KEY_USER_AGE, -1).takeIf { it != -1 }
        } else {
            null
        }
    }

    // Função para salvar o gênero do usuário
    fun saveUserGender(gender: String?) {
        sharedPreferences.edit().apply {
            putString(KEY_USER_GENDER, gender)
            apply()
        }
    }

    // Função para recuperar o gênero do usuário
    fun getUserGender(): String? {
        return sharedPreferences.getString(KEY_USER_GENDER, null)
    }

    // Função para salvar a data de nascimento do usuário
    fun saveUserBirthdate(birthdate: String) {
        sharedPreferences.edit().apply {
            putString(KEY_USER_BIRTHDATE, birthdate)
            apply()
        }
    }

    // Função para recuperar a data de nascimento do usuário
    fun getUserBirthdate(): String? {
        return sharedPreferences.getString(KEY_USER_BIRTHDATE, null)
    }

    // Função para salvar o telefone do usuário
    fun saveUserPhone(phone: String) {
        sharedPreferences.edit().apply {
            putString(KEY_USER_PHONE, phone)
            apply()
        }
    }

    // Função para recuperar o telefone do usuário
    fun getUserPhone(): String? {
        return sharedPreferences.getString(KEY_USER_PHONE, null)
    }

    // Função para salvar o email do usuário
    fun saveUserEmail(email: String) {
        sharedPreferences.edit().apply {
            putString(KEY_USER_EMAIL, email)
            apply()
        }
    }

    // Função para recuperar o email do usuário
    fun getUserEmail(): String? {
        return sharedPreferences.getString(KEY_USER_EMAIL, null)
    }

    // Função para salvar o ID do Facebook
    fun saveUserFacebookId(facebookId: String?) {
        sharedPreferences.edit().apply {
            putString(KEY_USER_FACEBOOK_ID, facebookId)
            apply()
        }
    }

    // Função para recuperar o ID do Facebook
    fun getUserFacebookId(): String? {
        return sharedPreferences.getString(KEY_USER_FACEBOOK_ID, null)
    }

    // Função para salvar o ID do Google
    fun saveUserGoogleId(googleId: String?) {
        sharedPreferences.edit().apply {
            putString(KEY_USER_GOOGLE_ID, googleId)
            apply()
        }
    }

    // Função para recuperar o ID do Google
    fun getUserGoogleId(): String? {
        return sharedPreferences.getString(KEY_USER_GOOGLE_ID, null)
    }

    // Função para salvar o código de confirmação
    fun saveUserConfirmationCode(code: String?) {
        sharedPreferences.edit().apply {
            putString(KEY_USER_CONFIRMATION_CODE, code)
            apply()
        }
    }

    // Função para recuperar o código de confirmação
    fun getUserConfirmationCode(): String? {
        return sharedPreferences.getString(KEY_USER_CONFIRMATION_CODE, null)
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
