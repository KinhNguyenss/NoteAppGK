package com.example.noteapppppppppppppp.view

import android.util.Log
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

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> = _imageUri

    fun addNote(title: String, description: String, imageUri: String? = null) {
        Log.d("NoteViewModel", "Adding note: title=$title, description=$description, imageUri=$imageUri")
        viewModelScope.launch {
            val newNote = Note(title = title, description = description, imageUri = imageUri)
            repository.insert(newNote)
            _imageUri.value = null
        }
    }

    fun updateNote(note: Note) {
        Log.d("NoteViewModel", "Updating note: id=${note.id}, title=${note.title}, description=${note.description}, imageUri=${note.imageUri}")
        viewModelScope.launch {
            repository.update(note)
            _imageUri.value = null
        }
    }

    fun deleteNote(note: Note) {
        Log.d("NoteViewModel", "Deleting note: id=${note.id}")
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    fun selectNote(note: Note?) {
        Log.d("NoteViewModel", "Selecting note: ${note?.title}, imageUri=${note?.imageUri}")
        _selectedNote.value = note
        _imageUri.value = note?.imageUri
    }

    fun setImageUri(uri: String?) {
        Log.d("NoteViewModel", "Setting imageUri: $uri")
        _imageUri.value = uri
    }
}