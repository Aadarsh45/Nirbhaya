package com.example.nirbhaya

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

class ContactViewModel(private val repository: ContactRepository) : ViewModel() {

    val allContactsFlow: LiveData<List<ContactModel>> = repository.getAllContactsFlow().asLiveData()

    fun insertContacts(contacts: List<ContactModel>) {
        viewModelScope.launch {
            repository.insertContacts(contacts)
        }
    }

    fun insertContact(contact: ContactModel) {
        viewModelScope.launch {
            repository.insertContact(contact)
        }
    }

    fun deleteContact(contact: ContactModel) {
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
    }

    fun updateContact(contact: ContactModel) {
        viewModelScope.launch {
            repository.updateContact(contact)
        }
    }
}

class ContactViewModelFactory(private val repository: ContactRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
