package com.example.expense_management_mvvm.repository

import com.example.expense_management_mvvm.base.BaseRepository
import com.example.expense_management_mvvm.base.DataState
import com.example.expense_management_mvvm.data.ExpenseManagement
import com.example.expense_management_mvvm.source.network.FirebaseService
import com.google.firebase.Timestamp

class HomeRepository(private val firebaseService: FirebaseService) : BaseRepository() {
    suspend fun addExpense(
        userId: String,
        title: String,
        date: Timestamp,
        details: String,
        price: Double,
        type: String
    ): DataState<Unit> {
        return getResults {
            firebaseService.addExpense(userId, title, date, details, price, type)
        }
    }
}