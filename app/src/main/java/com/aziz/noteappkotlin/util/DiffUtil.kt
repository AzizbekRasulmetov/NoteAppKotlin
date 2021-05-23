package com.aziz.noteappkotlin.util

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.aziz.noteappkotlin.database.Note

class DiffUtil(private val oldList: List<Note>, private val newList: List<Note>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return  oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].id == newList[newItemPosition].id &&
                oldList[oldItemPosition].name == newList[newItemPosition].name &&
                oldList[oldItemPosition].date == newList[newItemPosition].date &&
                oldList[oldItemPosition].priority == newList[newItemPosition].priority &&
                oldList[oldItemPosition].color == newList[newItemPosition].color)
        }
    }
