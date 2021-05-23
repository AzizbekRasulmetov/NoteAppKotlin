package com.aziz.noteappkotlin.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aziz.noteappkotlin.R
import com.aziz.noteappkotlin.databinding.FragmentNoteDetailBinding
import com.aziz.noteappkotlin.database.Note
import com.aziz.noteappkotlin.viewmodel.MainViewModel
import com.aziz.noteappkotlin.viewmodel.MainViewModelFactor


class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_detail, container, false)
        viewModel = ViewModelProvider(this, MainViewModelFactor(requireContext().applicationContext)).get(MainViewModel::class.java)

        //Get Note object from HomeFragment
        val note: Note = requireArguments().getParcelable("note")!!

        binding.noteNameTxt.text = note.name
        binding.deleteBtn.setOnClickListener {
            confirmDialog(getString(R.string.delete_message_confirm), note)
        }
        binding.doneBtn.setOnClickListener {
            confirmDialog(getString(R.string.done_confirm_message), note)
        }
        return binding.root
    }

    fun deleteNote(note: Note){
        viewModel.deleteNote(note)
    }

    fun confirmDialog(message: String, note: Note){
        AlertDialog.Builder(context)
            .setTitle(R.string.confirm)
            .setMessage(message)
            .setPositiveButton(R.string.confirm) { dialog, which ->
                deleteNote(note)
                dialog.dismiss()
                findNavController().navigate(R.id.action_noteDetailFragment_to_homeFragment)
            }
            .setNegativeButton(R.string.cancel) { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
    //TODO: Edit notes


}