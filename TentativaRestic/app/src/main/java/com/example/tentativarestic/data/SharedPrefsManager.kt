package com.example.tentativarestic.data

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

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
