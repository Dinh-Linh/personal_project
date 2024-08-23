package com.example.expense_management_mvvm.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.expense_management_mvvm.R
import com.example.expense_management_mvvm.activity.MainActivity
import com.example.expense_management_mvvm.activity.RefreshActivity
import com.example.expense_management_mvvm.base.BaseFragment
import com.example.expense_management_mvvm.base.BaseViewModel
import com.example.expense_management_mvvm.databinding.FragmentLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class LoginFragment :
    BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override val viewModel: BaseViewModel
        get() = ViewModelProvider(requireActivity())[BaseViewModel::class.java]

    override fun initData() {

    }

    override fun bindData() {

    }

    override fun observerData() {

    }

    override fun setOnClick() {
        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.login.setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

}