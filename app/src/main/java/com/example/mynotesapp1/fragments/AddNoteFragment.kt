package com.example.mynotesapp1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.mynotesapp1.MainActivity
import com.example.mynotesapp1.R
import com.example.mynotesapp1.databinding.FragmentAddNoteBinding
import com.example.mynotesapp1.model.Note
import com.example.mynotesapp1.viewmodel.NoteViewModel

class AddNoteFragment : Fragment(R.layout.fragment_add_note), MenuProvider {

    private var addNoteBinding: FragmentAddNoteBinding? = null
    private val binding get() = addNoteBinding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var addNoteView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addNoteBinding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        noteViewModel = (activity as MainActivity).noteViewModel
        addNoteView = view

    }

    private fun saveNote(view: View) {

        //Taking input from the user (Title & Desc)
        val noteTitle = binding.addNoteTitle.text.toString().trim()
        val noteDesc = binding.addNoteDesc.text.toString().trim()

        //checking, Is title present?
        if (noteTitle.isNotEmpty()) {

            val note = Note(0, noteTitle, noteDesc) //adding Title & Desc in the Database
            noteViewModel.addNote(note)

            Toast.makeText(addNoteView.context, "Note Saved.", Toast.LENGTH_LONG).show() // Navigate from current fragment to HomeFragment
            view.findNavController().popBackStack(R.id.homeFragment, false)

        } else {
            Toast.makeText(addNoteView.context, "Please enter the title!", Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

        menu.clear() // 1st Clear the existing items
        menuInflater.inflate(R.menu.menu_add_note, menu)  //Attach the menu Add note

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) { // itemId that is SAVE-MENU-ITEM
            R.id.saveMenu -> {
                saveNote(addNoteView) //saveNoteFunctionCalling
                true
            }

            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding = null
    }

}