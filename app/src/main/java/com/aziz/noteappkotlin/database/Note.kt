package com.aziz.noteappkotlin.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "note_table")
@Parcelize
data class Note(
    val name: String,
    val color: Int,
    val date: String,
    val priority: Int): Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}