package com.example.noteapppppppppppppp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteapppppppppppppp.data.NoteDatabase
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteapppppppppppppp.view.AuthViewModel
import com.example.noteapppppppppppppp.view.AuthViewModelFactory
import com.example.noteapppppppppppppp.view.LoginScreen
import com.example.noteapppppppppppppp.view.MainScreen
import com.example.noteapppppppppppppp.view.NoteViewModel
import com.example.noteapppppppppppppp.view.NoteViewModelFactory
import com.example.noteapppppppppppppp.view.RegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = NoteDatabase.getDatabase(this)
        val noteRepository = com.example.noteapppppppppppppp.data.NoteRepository(database.noteDao())
        val userRepository = com.example.noteapppppppppppppp.data.UserRepository(database.userDao())

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        val authViewModel: AuthViewModel = viewModel(
                            factory = AuthViewModelFactory(userRepository)
                        )
                        LoginScreen(navController = navController, viewModel = authViewModel)
                    }
                    composable("register") {
                        val authViewModel: AuthViewModel = viewModel(
                            factory = AuthViewModelFactory(userRepository)
                        )
                        RegisterScreen(navController = navController, viewModel = authViewModel)
                    }
                    composable("main") {
                        val noteViewModel: NoteViewModel = viewModel(
                            factory = NoteViewModelFactory(noteRepository)
                        )
                        MainScreen(viewModel = noteViewModel)
                    }
                }
            }
        }
    }
}