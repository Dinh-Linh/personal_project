package com.example.expense_management_mvvm.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.expense_management_mvvm.base.BaseViewModel
import com.example.expense_management_mvvm.repository.HomeRepository
import com.example.expense_management_mvvm.source.network.FirebaseService
import com.google.firebase.Timestamp

class HomeViewModel : BaseViewModel() {
    private val homeRepository = HomeRepository(FirebaseService())

    private val _addExpense = MutableLiveData<Result<Unit>>()
    val addExpenseResult: LiveData<Result<Unit>> get() = _addExpense

    fun addExpense(
        userId: String,
        title: String,
        date: Timestamp,
        details: String,
        price: Double,
        type: String
    ) {
        executeTask(
            request = {
                homeRepository.addExpense(userId, title, date, details, price, type)
            },
            onSuccess = {
                Log.d("HomeViewModel", "Add expense success")
                _addExpense.value = Result.success(Unit)
            },
            onError = { exception ->
                Log.e("HomeViewModel", "Add expense failed: ${exception.message}")
                _addExpense.value = Result.failure(exception)
            }
        )
    }

}