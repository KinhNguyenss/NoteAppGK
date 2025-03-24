package com.example.noteapppppppppppppp.view


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteapppppppppppppp.data.Note

@Composable
fun MainScreen(viewModel: NoteViewModel = viewModel()) {
    val notes by viewModel.notes.collectAsState()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val selectedNote by viewModel.selectedNote.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        // Input fields
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Buttons
        Row {
            Button(
                onClick = {
                    if (selectedNote == null) {
                        viewModel.addNote(title, description)
                    } else {
                        viewModel.updateNote(selectedNote!!.copy(title = title, description = description)) // lỗi
                        viewModel.selectNote(null)
                    }
                    title = ""
                    description = ""
                }
            ) {
                Text(if (selectedNote == null) "Add Note" else "Update Note")
            }
            Spacer(modifier = Modifier.width(8.dp))
            if (selectedNote != null) {
                Button(onClick = {
                    title = ""
                    description = ""
                    viewModel.selectNote(null)
                }) {
                    Text("Cancel")
                }
            }
        }

        // Notes list
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(notes) { note ->
                NoteItem(
                    note = note,
                    onClick = {
                        title = note.title
                        description = note.description
                        viewModel.selectNote(note)
                    },
                    onDelete = { viewModel.deleteNote(note) }
                )
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                Text(text = note.description, style = MaterialTheme.typography.bodyMedium)
            }
            Button(onClick = onDelete) {
                Text("Delete")
            }
        }
    }
}