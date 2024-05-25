package com.example.expense_management.data

import com.google.firebase.Timestamp

data class Revenue(
    val title: String? = null,
    val price: Double? = null,
    val date: Timestamp? = null,
    val content : String?= null,
)
