package com.aziz.noteappkotlin

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aziz.noteappkotlin.adapters.NoteAdapter
import com.aziz.noteappkotlin.databinding.ActivityMainBinding
import com.aziz.noteappkotlin.databinding.FragmentHomeBinding
import com.aziz.noteappkotlin.model.Note
import com.aziz.noteappkotlin.viewmodel.MainViewModel
import com.aziz.noteappkotlin.viewmodel.MainViewModelFactor
import kotlinx.android.synthetic.main.add_note_layout.view.*


class HomeFragment : Fragment(), NoteAdapter.NoteClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var dialogBuilder: AlertDialog
    private lateinit var adapter: NoteAdapter
    private lateinit var noteList: MutableList<Note>
    private lateinit var sortedList: MutableList<Note>
    private lateinit var viewModel: MainViewModel
    private lateinit var dialogView: View
    private lateinit var color: String
    private var priority = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        buildRecyclerView()
        buildAddDialog()
        checkBoxes()
        viewModel = ViewModelProvider(this, MainViewModelFactor(requireContext().applicationContext)).get(MainViewModel::class.java)
        binding.addFab.setOnClickListener {
            dialogBuilder.show()
        }
        getNotesFromDatabase()

        return binding.root
    }

    fun buildRecyclerView() {
        noteList = ArrayList()
        sortedList = ArrayList()
        adapter = NoteAdapter(sortedList, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
    }

    fun buildAddDialog() {
        dialogView = layoutInflater.inflate(R.layout.add_note_layout, null, false)
        dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)
            .setPositiveButton(
                getString(R.string.save)
            ) { _, _ ->
                createNote()
                dialogBuilder.hide()
            }
            .setNegativeButton(
                R.string.cancel
            ) { _, _ ->
                dialogBuilder.hide()
            }
            .create()
    }

    fun createNote() {
        val nameStr = dialogView.note_name_edit.text.toString()
        when (nameStr) {
            "" -> Toast.makeText(context, "Please add something", Toast.LENGTH_SHORT).show()
            else -> {
                setPriorityColor()
                Log.i("List", noteList.toString())
                val note = Note(nameStr, color.toInt(), priority)
                noteList.add(note)
                viewModel.addNote(note)
                sortByPriority()
                Log.i("List", noteList.toString())
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun checkBoxes() {
        dialogView.checkbox_very_important.setOnCheckedChangeListener { buttonView, isChecked ->
            if (dialogView.checkbox_very_important.isChecked) {
                dialogView.checkbox_important.isChecked = false
                dialogView.checkbox_not_so__important.isChecked = false
                dialogView.checkbox_not_important.isChecked = false
            }
        }
        dialogView.checkbox_not_important.setOnCheckedChangeListener { buttonView, isChecked ->
            if (dialogView.checkbox_not_important.isChecked) {
                dialogView.checkbox_important.isChecked = false
                dialogView.checkbox_not_so__important.isChecked = false
                dialogView.checkbox_very_important.isChecked = false
            }
        }
        dialogView.checkbox_important.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                dialogView.checkbox_not_so__important.isChecked = false
                dialogView.checkbox_very_important.isChecked = false
                dialogView.checkbox_not_important.isChecked = false
            }
        }
        dialogView.checkbox_not_so__important.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                dialogView.checkbox_very_important.isChecked = false
                dialogView.checkbox_not_important.isChecked = false
                dialogView.checkbox_important.isChecked = false
            }
        }
    }

    fun sortByPriority(){
        sortedList.clear()
        sortedList.addAll(noteList.sortedBy { it.priority })
    }

    fun setPriorityColor(){
        when {
            dialogView.checkbox_not_so__important.isChecked -> {
                color =  resources.getColor(R.color.green).toString()
                priority = 3
            }
            dialogView.checkbox_not_important.isChecked -> {
                color =  resources.getColor(R.color.light_green).toString()
                priority = 4
            }
            dialogView.checkbox_important.isChecked -> {
                color = resources.getColor(R.color.orange).toString()
                priority = 2
            }
            dialogView.checkbox_very_important.isChecked -> {
                color =  resources.getColor(R.color.red).toString()
                priority = 1
            }
        }
    }

    fun getNotesFromDatabase(){
        viewModel.getAllNotes()
        viewModel.noteList.observe(requireActivity(), Observer {
            noteList.clear()
            noteList.addAll(it)
            sortByPriority()
            adapter.notifyDataSetChanged()
        })
    }

    override fun onNoteClicked(position: Int) {
        val note = sortedList[position]
        val bundle: Bundle = bundleOf("name" to note.name)
        findNavController().navigate(R.id.action_homeFragment_to_noteDetailFragment, bundle)
    }

}