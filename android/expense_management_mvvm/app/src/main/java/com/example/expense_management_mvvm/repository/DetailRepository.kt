package com.example.expense_management_mvvm.repository

import com.example.expense_management_mvvm.base.BaseRepository
import com.example.expense_management_mvvm.base.DataState
import com.example.expense_management_mvvm.data.ExpenseManagement
import com.example.expense_management_mvvm.source.network.FirebaseService
import com.google.firebase.Timestamp

class DetailRepository(private val firebaseService: FirebaseService) : BaseRepository() {
    suspend fun getItem(title : String, month: Timestamp) : DataState<List<ExpenseManagement>> {
        return getResults {
            firebaseService.getAllItem(title, month)
        }
    }
}