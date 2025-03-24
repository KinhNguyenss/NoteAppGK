package com.example.noteapppppppppppppp.view


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapppppppppppppp.data.Note
import com.example.noteapppppppppppppp.data.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val notes: StateFlow<List<Note>> = repository.allNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote: StateFlow<Note?> = _selectedNote

    fun addNote(title: String, description: String) {
        viewModelScope.launch {
            val newNote = Note(title = title, description = description)
            repository.insert(newNote)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.update(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    fun selectNote(note: Note?) {
        _selectedNote.value = note
    }
}