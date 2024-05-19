package com.example.expense_management.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.expense_management.R
import com.example.expense_management.databinding.ActivityHomeBinding
import com.example.expense_management.fragment.ExpenseFragment
import com.example.expense_management.fragment.RevenueFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class HomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val auth = Firebase.auth
    private val revenueFragment = RevenueFragment()
    private val expenseFragment = ExpenseFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.commit {
            replace(binding.fmContainer.id, revenueFragment)
            setReorderingAllowed(true)
        }
        binding.revenue.setOnClickListener{
            supportFragmentManager.commit {
                replace(binding.fmContainer.id, revenueFragment)
                setReorderingAllowed(true)
            }
        }
        binding.expense.setOnClickListener{
            supportFragmentManager.commit {
                replace(binding.fmContainer.id, expenseFragment)
                setReorderingAllowed(true)
            }
        }

        binding.signOut.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}