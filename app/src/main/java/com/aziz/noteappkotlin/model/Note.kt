package com.aziz.noteappkotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    val name: String,
    val color: Int,
    val priority: Int){

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}