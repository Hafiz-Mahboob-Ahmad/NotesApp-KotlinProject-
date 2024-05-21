package com.example.mynotesapp1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotesapp1.model.Note
import com.example.mynotesapp1.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(val app: Application, private val noteRepository: NoteRepository) :
    AndroidViewModel(app) {

    fun addNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }

    fun getAllNotes() = noteRepository.getAllNotes()

    fun searchNote(query: String?) = noteRepository.searchNote(query)
}