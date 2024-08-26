package com.example.expense_management_mvvm.repository

import com.example.expense_management_mvvm.base.BaseRepository
import com.example.expense_management_mvvm.base.DataState
import com.example.expense_management_mvvm.data.User
import com.example.expense_management_mvvm.source.network.FirebaseService
import com.google.firebase.auth.FirebaseUser

class AuthRepository(private val firebaseService: FirebaseService) : BaseRepository() {

    suspend fun register(
        email: String,
        password: String,
        username: String
    ): DataState<FirebaseUser?> {
        return getResults {
            firebaseService.register(email, password, username)
        }
    }

    suspend fun login(email: String, password: String): DataState<FirebaseUser?> {
        val result = getResults {
            firebaseService.login(email, password)
        }
        return result
    }

    suspend fun getUser(userId: String): DataState<User?> {
        val result =
         getResults {
            firebaseService.getUser(userId)
        }
        return result
    }
}