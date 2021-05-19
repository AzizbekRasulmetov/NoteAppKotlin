package com.aziz.noteappkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_note_detail.*
import kotlinx.android.synthetic.main.fragment_note_detail.view.*


class NoteDetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_note_detail, container, false)
        var names = requireArguments().getString("name")
        view.note_name_txt.text = names
        return view
    }

    //TODO: SplashScreen, Delete, Edit notes


}