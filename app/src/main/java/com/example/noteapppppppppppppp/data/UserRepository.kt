package com.example.noteapppppppppppppp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {
    suspend fun register(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val existingUser = userDao.getUser(username, password)
            if (existingUser == null) {
                userDao.insert(User(username, password))
                true
            } else {
                false // Người dùng đã tồn tại
            }
        }
    }

    suspend fun login(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val user = userDao.getUser(username, password)
            user != null
        }
    }
}