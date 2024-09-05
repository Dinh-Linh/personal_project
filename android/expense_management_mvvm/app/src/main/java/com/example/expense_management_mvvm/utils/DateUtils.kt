package com.example.expense_management_mvvm.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

open class DateUtils(private val context: Context) {
    private var date: Date = Date()
    private var currentDate: Calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val monthFormat = SimpleDateFormat("MM/yyyy", Locale.getDefault())

    fun formatDate(): String {
        val currentDate = Calendar.getInstance().time
        val formatDate = dateFormat.format(currentDate)
        return formatDate
    }

    fun formatMonth() : String{
        val currentMonth = currentDate.time
        val formatMonth = monthFormat.format(currentMonth)
        return formatMonth
    }

    fun convertToTimeStamp(dateString: String): Timestamp? {
        val timestamp = dateFormat.parse(dateString)
        return timestamp?.let { Timestamp(it) }
    }

    fun prevDate(): String {
        currentDate.add(Calendar.DAY_OF_YEAR, -1)
        date = currentDate.time
        return dateFormat.format(date)
    }

    fun prevMonth() : String{
        currentDate.add(Calendar.MONTH, -1)
        date = currentDate.time
        return monthFormat.format(date)
    }

    fun nextMonth() : String{
        currentDate.add(Calendar.MONTH, 1)
        date = currentDate.time
        return monthFormat.format(date)
    }

    fun nextDate(): String {
        currentDate.add(Calendar.DAY_OF_YEAR, 1)
        date = currentDate.time
        return dateFormat.format(date)
    }

    fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val initYear = calendar.get(Calendar.YEAR)
        val initMonth = calendar.get(Calendar.MONTH)
        val initDay = calendar.get(Calendar.DAY_OF_MONTH)

        val showDatePicker = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)
                date = selectedDate.time
                val formattedDate = dateFormat.format(date)
                onDateSelected(formattedDate)
            },
            initYear,
            initMonth,
            initDay
        )
        showDatePicker.show()
    }

}