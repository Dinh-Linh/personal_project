package com.example.expense_management.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.commit
import com.example.expense_management.R
import com.example.expense_management.data.Expense
import com.example.expense_management.data.Revenue
import com.example.expense_management.data.User
import com.example.expense_management.databinding.ActivityHomeBinding
import com.example.expense_management.fragment.ExpenseFragment
import com.example.expense_management.fragment.RevenueFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class HomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val revenueFragment = RevenueFragment()
    private val expenseFragment = ExpenseFragment()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth.currentUser?.let { user ->
            db.collection("users").document(user.uid)
                .get().addOnSuccessListener { document ->
                    val user = document.toObject<User>()
                    binding.username.text = "Hello ${user?.name.toString()}"
                }
        }
        supportFragmentManager.commit {
            replace(binding.fmContainer.id, revenueFragment)
            setReorderingAllowed(true)
        }
        binding.revenue.setOnClickListener {
            supportFragmentManager.commit {
                replace(binding.fmContainer.id, revenueFragment)
                setReorderingAllowed(true)
            }
        }
        binding.expense.setOnClickListener {
            supportFragmentManager.commit {
                replace(binding.fmContainer.id, expenseFragment)
                setReorderingAllowed(true)
            }
        }

        binding.signOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.thongKe.setOnClickListener {
            auth.currentUser?.let { expense ->
                db.collection("total_expense").get()
                    .addOnSuccessListener { documents ->
                        val listExpense = mutableListOf<Expense>()
                        for (document in documents) {
                            val expense = document.toObject<Expense>()
                            listExpense.add(expense)
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
                    }
            }
            auth.currentUser?.let { expense ->
                db.collection("total_revenue").get()
                    .addOnSuccessListener { documents ->
                        val listRevenue = mutableListOf<Revenue>()
                        for (document in documents) {
                            val revenue = document.toObject<Revenue>()
                            listRevenue.add(revenue)
                        }
                        println(listRevenue)
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
                    }
            }
            startActivity(Intent(this, MonthlyReportActivity::class.java))
        }
    }
}