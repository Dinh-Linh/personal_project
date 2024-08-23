package com.example.expense_management_mvvm.data

import com.google.firebase.Timestamp

data class ExpenseManagement (
    val title:String?= null,
    val date: Timestamp ?= null,
    val details: String?= null,
    val price: Double?= null,
    val type:String?= null
)
