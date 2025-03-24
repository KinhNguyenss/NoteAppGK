package com.example.noteapppppppppppppp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val username: String,
    val password: String // Lưu ý: Trong thực tế, nên mã hóa password
)