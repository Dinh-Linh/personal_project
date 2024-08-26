package com.example.expense_management_mvvm.fragment

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.expense_management_mvvm.R
import com.example.expense_management_mvvm.activity.MainActivity
import com.example.expense_management_mvvm.base.BaseFragment
import com.example.expense_management_mvvm.databinding.FragmentLoginBinding
import com.example.expense_management_mvvm.view_model.AuthViewModel

class LoginFragment :
    BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override val viewModel: AuthViewModel
        get() = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

    override fun initData() {

    }

    override fun bindData() {

    }

    override fun observerData() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
                Toast.makeText(
                    requireContext(),
                    "Dang nhap thanh cong",
                    Toast.LENGTH_LONG
                ).show()
                viewModel.getUser(user.uid)
            }
            else{
                Toast.makeText(
                    requireContext(),
                    "Ten dang nhap hoac mat khau khong dung",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun setOnClick() {
        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.login.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Vui long nhap ten dang nhap va mat khau",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                viewModel.login(email, password)
            }

        }
    }

}