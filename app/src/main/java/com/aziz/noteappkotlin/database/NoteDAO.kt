package com.aziz.noteappkotlin.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aziz.noteappkotlin.model.Note

@Dao
interface NoteDAO {

    @Insert()
    suspend fun insert(note: Note)

    @Delete()
    suspend fun delete(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_table")
    suspend fun getAllNotes(): List<Note>
}