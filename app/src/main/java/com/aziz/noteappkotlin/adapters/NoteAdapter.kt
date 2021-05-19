package com.aziz.noteappkotlin.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aziz.noteappkotlin.R
import com.aziz.noteappkotlin.model.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter(
    private var list: List<Note>,
    private var listener: NoteClickListener
) : RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),  View.OnClickListener {

        init {
            itemView.note_layout.setOnClickListener(this)
        }

        fun onBind(note: Note) {
            itemView.note_name_txt.text = note.name
            itemView.importance_color.setBackgroundColor(note.color)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onNoteClicked(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface NoteClickListener {
        fun onNoteClicked(position: Int)
    }
}