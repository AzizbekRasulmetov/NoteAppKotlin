package com.aziz.noteappkotlin.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aziz.noteappkotlin.database.Note
import com.aziz.noteappkotlin.repository.Repository
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(context: Context) : ViewModel() {

    var repository = Repository(context)
    var noteList: MutableLiveData<List<Note>> = MutableLiveData()

    fun addNote(note: Note) {
        viewModelScope.launch {
            repository.addNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            repository.deleteAllNotes()
        }
    }

    fun getAllNotes(): MutableLiveData<List<Note>> {
        viewModelScope.launch {
            withContext(Main) {
                noteList.value = repository.getAllNotes().value
            }
        }
        return noteList
    }

}