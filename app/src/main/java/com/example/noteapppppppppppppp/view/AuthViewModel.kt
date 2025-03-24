package com.example.noteapppppppppppppp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapppppppppppppp.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun register(username: String, password: String) {
        viewModelScope.launch {
            val success = userRepository.register(username, password)
            if (success) {
                _isAuthenticated.value = true
                _errorMessage.value = null
            } else {
                _errorMessage.value = "Username already exists"
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val success = userRepository.login(username, password)
            if (success) {
                _isAuthenticated.value = true
                _errorMessage.value = null
            } else {
                _errorMessage.value = "Invalid username or password"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}