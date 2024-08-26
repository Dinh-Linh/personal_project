package com.example.expense_management_mvvm.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.expense_management_mvvm.R
import com.example.expense_management_mvvm.base.BaseFragment
import com.example.expense_management_mvvm.base.BaseViewModel
import com.example.expense_management_mvvm.databinding.FragmentHomeBinding
import com.example.expense_management_mvvm.fragment.child_fragment.ExpenseFragment
import com.example.expense_management_mvvm.fragment.child_fragment.RevenueFragment
import com.example.expense_management_mvvm.view_model.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: AuthViewModel
        get() = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

    private val fmExpense = ExpenseFragment()
    private val fmRevenue = RevenueFragment()

    override fun initData() {

    }

    override fun bindData() {

    }

    @SuppressLint("SetTextI18n")
    override fun observerData() {
        viewModel.username.observe(viewLifecycleOwner) { username ->
            if (username != null) {
                binding.username.text = "Hi ${username.username}"
                Log.d("Username: ", username.toString())
            } else {
                Log.d("Username", "Username not found")
            }
        }
    }

    @SuppressLint("CommitTransaction")
    override fun setOnClick() {
        val currentFm = childFragmentManager.findFragmentById(R.id.fmContainer)
        childFragmentManager.beginTransaction().replace(R.id.fmContainer, fmRevenue)
            .addToBackStack(null).commit()
        binding.revenue.setOnClickListener {
            if (currentFm != fmRevenue) {
                childFragmentManager.beginTransaction().replace(R.id.fmContainer, fmRevenue)
                    .addToBackStack(null).commit()
            }
        }
        binding.expense.setOnClickListener {
            if (currentFm != fmExpense) {
                childFragmentManager.beginTransaction().replace(R.id.fmContainer, fmExpense)
                    .addToBackStack(null).commit()
            }
        }
    }


}