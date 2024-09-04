package com.example.expense_management_mvvm.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.expense_management_mvvm.R
import com.example.expense_management_mvvm.activity.RefreshActivity
import com.example.expense_management_mvvm.base.BaseFragment
import com.example.expense_management_mvvm.base.BaseViewModel
import com.example.expense_management_mvvm.data.User
import com.example.expense_management_mvvm.databinding.FragmentHomeBinding
import com.example.expense_management_mvvm.fragment.child_fragment.ExpenseFragment
import com.example.expense_management_mvvm.fragment.child_fragment.RevenueFragment
import com.example.expense_management_mvvm.view_model.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: AuthViewModel
        get() = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

    private val fmExpense = ExpenseFragment()
    private val fmRevenue = RevenueFragment()
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    override fun initData() {

    }

    @SuppressLint("SetTextI18n")
    override fun bindData() {

        auth.currentUser?.let { user ->
            db.collection("user").document(user.uid).get().addOnSuccessListener { docs ->
                val user = docs.toObject<User>()
                binding.username.text = "Hi ${user?.username}"
            }.addOnFailureListener { ex ->
                ex.message
            }
        }
    }

    override fun observerData() {

    }

    @SuppressLint("CommitTransaction", "SetTextI18n")
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

        binding.signOut.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(requireActivity(), RefreshActivity::class.java))
            requireActivity().finish()
        }
    }


}