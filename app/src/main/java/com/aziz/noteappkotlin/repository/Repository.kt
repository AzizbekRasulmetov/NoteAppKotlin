package com.aziz.noteappkotlin.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.aziz.noteappkotlin.database.NoteDAO
import com.aziz.noteappkotlin.database.NoteDatabase
import com.aziz.noteappkotlin.database.Note
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext

object Repository {

    var list: MutableLiveData<List<Note>> = MutableLiveData()
    lateinit var dao: NoteDAO

    operator fun invoke(context: Context): Repository {
        dao = NoteDatabase.getInstance(context).noteDao()
        return this
    }

    suspend fun addNote(note: Note) {
        dao.insert(note)
    }

    suspend fun deleteNote(note: Note){
        dao.delete(note)
    }

    suspend fun deleteAllNotes(){
        dao.deleteAll()
    }

    suspend fun getAllNotes(): MutableLiveData<List<Note>>{
        val notes = dao.getAllNotes()
        withContext(Main){
            list.value = notes
        }
        return list
    }

}