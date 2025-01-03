package com.example.tentativarestic.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tentativarestic.data.AppDatabase
import com.example.tentativarestic.data.DataRepository
import com.example.tentativarestic.entities.Unidade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UnidadeViewModel(private val database: AppDatabase, private val dataRepository: DataRepository) : ViewModel() {
    private val unidadeDao = database.unidadeDao()

//    val unidades: StateFlow<List<Unidade>> = unidadeDao.getAllUnidades()
//        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _unidades = MutableLiveData<List<Unidade>>()
    val unidades: LiveData<List<Unidade>> get() = _unidades

    fun fetchUnidadesByCursoNome(cursoNome: String) {
        viewModelScope.launch(Dispatchers.IO) { // Executa em uma thread de I/O
            val fetchedUnidades = dataRepository.getUnidadesByCursoNome(cursoNome)

            withContext(Dispatchers.Main) {
                // Log do número de unidades após a resposta ser processada
                Log.d("UnidadesFetched", "Número de unidades: ${fetchedUnidades.size}")

                // Log das unidades fetchadas
                Log.d("UnidadesFetched", "Unidades fetchadas: $fetchedUnidades")

                // Atualiza o LiveData na thread principal
                _unidades.postValue(fetchedUnidades)
            }
        }
    }


    fun addUnidade(unidade: Unidade) {
        viewModelScope.launch {
            unidadeDao.insertUnidade(unidade)
        }
    }

    fun updateUnidade(unidade: Unidade) {
        viewModelScope.launch {
            unidadeDao.updateUnidade(unidade)
        }
    }

    fun deleteUnidade(unidade: Unidade) {
        viewModelScope.launch {
            unidadeDao.deleteUnidade(unidade)
        }
    }
}
