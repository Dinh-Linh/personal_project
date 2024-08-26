package com.example.expense_management_mvvm.repository

import com.example.expense_management_mvvm.base.BaseRepository
import com.example.expense_management_mvvm.source.network.FirebaseService

class HomeRepository(private val firebaseService: FirebaseService) : BaseRepository() {

}