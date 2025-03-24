package com.example.noteapppppppppppppp.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.noteapppppppppppppp.data.Note
import android.util.Log

@Composable
fun MainScreen(viewModel: NoteViewModel = viewModel()) {
    val notes by viewModel.notes.collectAsState()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val selectedNote by viewModel.selectedNote.collectAsState()
    val imageUri by viewModel.imageUri.collectAsState()

    // Launcher để mở file picker
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.toString()?.let { uriString ->
            Log.d("MainScreen", "Selected Image URI: $uriString")
            viewModel.setImageUri(uriString)
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
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
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                imagePickerLauncher.launch(arrayOf("image/*"))
            }
        ) {
            Text("Pick Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = {
                    val currentSelectedNote = selectedNote
                    if (currentSelectedNote == null) {
                        Log.d("MainScreen", "Adding note with imageUri: $imageUri")
                        viewModel.addNote(title, description, imageUri)
                    } else {
                        Log.d("MainScreen", "Updating note with imageUri: $imageUri")
                        viewModel.updateNote(
                            currentSelectedNote.copy(
                                title = title,
                                description = description,
                                imageUri = imageUri
                            )
                        )
                        viewModel.selectNote(null)
                    }
                    title = ""
                    description = ""
                    viewModel.setImageUri(null) // Reset imageUri sau khi lưu
                }
            ) {
                Text(if (selectedNote == null) "Add Note" else "Update Note")
            }
            Spacer(modifier = Modifier.width(8.dp))
            if (selectedNote != null) {
                Button(onClick = {
                    title = ""
                    description = ""
                    viewModel.setImageUri(null) // Reset imageUri khi hủy
                    viewModel.selectNote(null)
                }) {
                    Text("Cancel")
                }
            }
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(notes) { note ->
                NoteItem(
                    note = note,
                    onClick = {
                        title = note.title
                        description = note.description
                        viewModel.setImageUri(note.imageUri) // Load imageUri khi chọn note
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
            note.imageUri?.let { uri ->
                Log.d("NoteItem", "Displaying image for note ${note.title}: $uri")
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Note Image",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 8.dp),
                    contentScale = ContentScale.Crop
                )
            }
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