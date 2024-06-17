package com.example.expense_management.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expense_management.R
import com.example.expense_management.adapter.ExpenseAdapter
import com.example.expense_management.databinding.ActivityCatalogDetailsBinding
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore

class CatalogDetailsActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCatalogDetailsBinding.inflate(layoutInflater) }
    private val db = Firebase.firestore
    private lateinit var expenseAdapter: ExpenseAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val ml = MonthlyReportActivity()
        val categoryName = intent.getStringExtra("category_name") ?: ""
        val startDateTimestamp = intent.getParcelableExtra<Timestamp>("start_date")
        val endDateTimestamp = intent.getParcelableExtra<Timestamp>("end_date")

        if (startDateTimestamp != null && endDateTimestamp != null) {
            ml.fetchData(
                "total_expense",
                categoryName,
                startDateTimestamp,
                endDateTimestamp
            ) { expenseList ->
                expenseAdapter = ExpenseAdapter(expenseList)
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.recyclerView.adapter = expenseAdapter
            }
        }
    }
}