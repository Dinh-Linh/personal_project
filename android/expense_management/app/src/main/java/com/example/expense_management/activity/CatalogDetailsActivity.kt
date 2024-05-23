package com.example.expense_management.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.expense_management.R
import com.example.expense_management.databinding.ActivityCatalogDetailsBinding

class CatalogDetailsActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCatalogDetailsBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}