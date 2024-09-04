package com.example.expense_management_mvvm.view_model

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.expense_management_mvvm.base.BaseViewModel
import com.example.expense_management_mvvm.data.User
import com.example.expense_management_mvvm.repository.AuthRepository
import com.example.expense_management_mvvm.source.network.FirebaseService
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : BaseViewModel() {
    private val authRepository = AuthRepository(FirebaseService())
    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> get() = _user

    private val _username = MutableLiveData<User?>()
    val username:LiveData<User?> get() = _username

    fun login(email: String, password: String) {
        executeTask(
            request = { authRepository.login(email, password) },
            onSuccess = {user ->
                _user.value = user
                user?.uid?.let { getUser(it) }
            },
            onError = { exception ->
                exception.message
            }
        )
    }

    fun register(email: String, password: String, username: String) {
        executeTask(
            request = {
                authRepository.register(email, password, username)
            },
            onSuccess = {
                _user.value = it
            },
            onError = { exception ->
                exception.message
            }
        )
    }

    fun getUser(userId:String) {
        executeTask(
            request = {
                authRepository.getUser(userId)
            },
            onSuccess = {username ->
                _username.value = username
            },
            onError = {exception ->
                Log.e("AuthViewModel", "Error fetching username: ${exception.message}")

            }
        )
    }

    fun logout(){
        executeTask(
            request = {
                authRepository.logout()
            },
            onSuccess = {

            },
            onError = {

            }
        )
    }

}