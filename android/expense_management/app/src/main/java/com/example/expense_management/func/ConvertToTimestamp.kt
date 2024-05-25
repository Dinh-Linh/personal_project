package com.example.expense_management.func

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class ConvertToTimestamp {
    fun convertToTimestamp(dateString : String) : Timestamp? {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = format.parse(dateString)
        return date?.let { Timestamp(it) }
    }
}