package com.aziz.noteappkotlin.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aziz.noteappkotlin.R
import com.aziz.noteappkotlin.database.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter(
    private var listener: NoteClickListener
) : RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {

    private var oldList : MutableList<Note> = mutableListOf()

    fun setNoteList(list: List<Note>){
        Log.d("MyUtil", " ${oldList.size} : ${list.size}")
        val diffUtil = com.aziz.noteappkotlin.util.DiffUtil(oldList, list)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldList.clear()
        oldList.addAll(list)
        diffResults.dispatchUpdatesTo(this)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        init {
            itemView.note_layout.setOnClickListener(this)
        }

        fun onBind(note: Note) {
            itemView.note_name_txt.text = note.name
            itemView.importance_color.setBackgroundColor(note.color)
            itemView.date_txt.text = note.date
            itemView.pop_up_menu.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            if (v == itemView.pop_up_menu) {
                if (position != RecyclerView.NO_POSITION) {
                    listener.onPopupClicked(itemView.pop_up_menu, position)
                }
            } else {
                if (position != RecyclerView.NO_POSITION) {
                    listener.onNoteClicked(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(oldList[position])
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    interface NoteClickListener {
        fun onNoteClicked(position: Int)

        fun onPopupClicked(view: View, position: Int)
    }
}