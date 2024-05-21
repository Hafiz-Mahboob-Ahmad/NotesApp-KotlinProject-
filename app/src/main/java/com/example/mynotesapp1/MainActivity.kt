package com.example.mynotesapp1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mynotesapp1.database.NoteDatabase
import com.example.mynotesapp1.databinding.ActivityMainBinding
import com.example.mynotesapp1.repository.NoteRepository
import com.example.mynotesapp1.viewmodel.NoteVMFactory
import com.example.mynotesapp1.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()

    }

    private fun setupViewModel(){

        val noteRepository = NoteRepository(NoteDatabase(this))
        val viewModelProviderFactory = NoteVMFactory(application, noteRepository)
        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]

    }

}