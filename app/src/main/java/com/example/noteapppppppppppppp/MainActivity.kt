package com.example.noteapppppppppppppp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteapppppppppppppp.data.NoteDatabase
import com.example.noteapppppppppppppp.data.NoteRepository
import com.example.noteapppppppppppppp.view.MainScreen
import com.example.noteapppppppppppppp.view.NoteViewModel
import com.example.noteapppppppppppppp.view.NoteViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = NoteDatabase.getDatabase(this)
        val repository = NoteRepository(database.noteDao())

        setContent {
            MaterialTheme {
                val viewModel: NoteViewModel = viewModel(factory = NoteViewModelFactory(repository))
                MainScreen(viewModel = viewModel)
            }
        }
    }
}