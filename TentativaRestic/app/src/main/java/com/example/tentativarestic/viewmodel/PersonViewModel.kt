package com.example.tentativarestic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tentativarestic.data.AppDatabase
import com.example.tentativarestic.entities.Person
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PersonViewModel(private val database: AppDatabase) : ViewModel() {
    private val personDao = database.personDao()

    val persons: StateFlow<List<Person>> = personDao.getAllPersons()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addPerson(person: Person) {
        viewModelScope.launch {
            personDao.insertPerson(person)
        }
    }

    fun updatePerson(person: Person) {
        viewModelScope.launch {
            personDao.updatePerson(person)
        }
    }

    fun deletePerson(person: Person) {
        viewModelScope.launch {
            personDao.deletePerson(person)
        }
    }
}
