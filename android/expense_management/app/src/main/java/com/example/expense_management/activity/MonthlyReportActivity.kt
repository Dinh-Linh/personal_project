package com.example.expense_management.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.expense_management.R
import com.example.expense_management.data.Revenue
import com.example.expense_management.databinding.ActivityMonthlyReportBinding
import com.example.expense_management.func.ConvertToTimestamp
import com.example.expense_management.func.DateUtils
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class MonthlyReportActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMonthlyReportBinding.inflate(layoutInflater) }
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Set date
        var startDate = binding.startDate.text.toString()
        var endDate = binding.endDate.text.toString()
        val dateUtils = DateUtils(this@MonthlyReportActivity)
        binding.startDate.text = dateUtils.getCurrentDate()
        binding.startDate.setOnClickListener {
            dateUtils.showDatePickerDialog { selectedDate ->
                startDate = selectedDate
                binding.startDate.text = startDate
            }
        }
        binding.endDate.text = dateUtils.getCurrentDate()
        binding.endDate.setOnClickListener {
            dateUtils.showDatePickerDialog { selectedDate ->
                endDate = selectedDate
                binding.endDate.text = endDate
            }
        }


        /*   if (endDateTimestamp != null && startDateTimestamp != null) {
               db.collection("total_revenue").whereLessThan("date", endDateTimestamp)
                   .whereGreaterThan("date", startDateTimestamp).get()
                   .addOnSuccessListener { documents ->
                       val listRevenue = mutableListOf<Revenue>()
                       var totalRevenue = 0.0
                       for (document in documents) {
                           val revenue = document.toObject<Revenue>()
                           listRevenue.add(revenue)
                           totalRevenue += revenue.price!!
                           binding.totalRevenue.text = totalRevenue.toString() + " VND"
                           //println(totalRevenue)
                       }
                   }
                   .addOnFailureListener { exception ->
                       Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
                   }

           }*/
        db.collection("total_revenue").whereEqualTo("title", "Tiền lương").get()
            .addOnSuccessListener { documents ->
                val list = mutableListOf<Revenue>()
                var totalPrice = 0.0
                for (document in documents) {
                    val revenue = document.toObject<Revenue>()
                    list.add(revenue)
                    totalPrice += revenue.price!!
                }
            }
        binding.btnConfirm.setOnClickListener {
            val startDateTimestamp = ConvertToTimestamp().convertToTimestamp(startDate)
            val endDateTimestamp = ConvertToTimestamp().convertToTimestamp(endDate)
            if (startDateTimestamp != null && endDateTimestamp != null) {
                totalPriceRevenue(
                    "Tiền lương",
                    startDateTimestamp,
                    endDateTimestamp
                ) { totalPrice ->
                    binding.tienLuong.text = "$totalPrice VND"
                }
            }
        }
    }

    //Total money func
    fun totalPriceRevenue(
        vlue: String,
        startDate: Timestamp,
        endDate: Timestamp,
        callback: (Double) -> Unit
    ) {
        var totalPrice = 0.0
        db.collection("total_revenue").whereEqualTo("title", vlue)
            .whereGreaterThan("date", startDate).whereLessThan("date", endDate).get()
            .addOnSuccessListener { documents ->
                val list = mutableListOf<Revenue>()
                for (document in documents) {
                    val revenue = document.toObject<Revenue>()
                    list.add(revenue)
                    totalPrice += revenue.price!!
                }
                callback(totalPrice)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
            }
    }
}


