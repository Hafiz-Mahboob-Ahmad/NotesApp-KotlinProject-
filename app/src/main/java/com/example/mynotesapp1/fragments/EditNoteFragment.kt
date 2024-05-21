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
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mynotesapp1.MainActivity
import com.example.mynotesapp1.R
import com.example.mynotesapp1.databinding.FragmentEditNoteBinding
import com.example.mynotesapp1.model.Note
import com.example.mynotesapp1.viewmodel.NoteViewModel

class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {

    private var editNotebinding: FragmentEditNoteBinding? = null
    private val binding get() = editNotebinding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNoteDataClass: Note

    private val args: EditNoteFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        editNotebinding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel = (activity as MainActivity).noteViewModel
        currentNoteDataClass = args.note!!  //args.note!!.also { currentNoteDataClass = it }

        binding.editNoteTitle.setText(currentNoteDataClass.noteTitle)
        binding.editNoteDesc.setText(currentNoteDataClass.noteDesc)

        binding.editNoteFab.setOnClickListener {
            val noteTitle = binding.editNoteTitle.text.toString().trim()
            val noteDesc = binding.editNoteDesc.text.toString().trim()

            if (noteTitle.isNotEmpty()){
                val note = Note(currentNoteDataClass.id,noteTitle,noteDesc)
                notesViewModel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment, false)
            }else{
                Toast.makeText(context, "Please enter note title!", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun deleteNote() {
        activity?.let { activity -> // Check if the activity is not null
            AlertDialog.Builder(activity).apply {  // Create an AlertDialog using the activity context
                setTitle("Delete Note")   // Set the title and message of the dialog
                setMessage("Do you want to delete this note?")
                setPositiveButton("Delete") { _,_ -> // Set the action for the positive button (Delete)
                    notesViewModel.deleteNote(currentNoteDataClass )  // Call deleteNote() function of NoteViewModel to delete the note
                    Toast.makeText(context, "Note Deleted.", Toast.LENGTH_LONG).show()  // Show a toast message indicating note deletion
                    view?.findNavController()?.popBackStack(R.id.homeFragment, false) // Navigate back to the home fragment
                }
                setNegativeButton("Cancel", null) // Set the action for the negative button (Cancel)
            }.create().show() // Create and display the AlertDialog
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                deleteNote()
                true
            } else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
            editNotebinding = null
    }


}