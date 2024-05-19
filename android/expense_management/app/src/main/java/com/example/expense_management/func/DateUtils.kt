package com.example.expense_management.func

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DateUtils(private val context: Context) {
    private var currentDate: Date = Date()
    private val formatDate = SimpleDateFormat("dd/MM/yyyy")
    fun getCurrentDate(): String {
        return formatDate.format(currentDate)
    }
    fun getPreDate(): String {
        val calendarpreDate = Calendar.getInstance()
        calendarpreDate.time = currentDate
        calendarpreDate.add(Calendar.DAY_OF_YEAR, -1)
        val preDate = calendarpreDate.time
        return formatDate.format(preDate)
    }

    fun getNextDate(): String {
        val calendarNextDate = Calendar.getInstance()
        calendarNextDate.time = currentDate
        calendarNextDate.add(Calendar.DAY_OF_YEAR, 1)
        val nextDate = calendarNextDate.time
        return formatDate.format(nextDate)
    }

    fun showDatePickerDialog(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)
                currentDate = selectedDate.time
                val formattedDate = formatDate.format(currentDate)
                onDateSelected(formattedDate)
            },
            initialYear,
            initialMonth,
            initialDay
        )

        datePickerDialog.show()
    }

}