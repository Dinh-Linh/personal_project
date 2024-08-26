package com.example.expense_management_mvvm.fragment

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.expense_management_mvvm.R
import com.example.expense_management_mvvm.base.BaseFragment
import com.example.expense_management_mvvm.base.BaseViewModel
import com.example.expense_management_mvvm.databinding.FragmentRegisterBinding
import com.example.expense_management_mvvm.view_model.AuthViewModel


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    override val viewModel : AuthViewModel
        get() = ViewModelProvider(this)[AuthViewModel::class.java]
    override fun initData() {

    }

    override fun bindData() {

    }

    override fun observerData() {
        viewModel.user.observe(viewLifecycleOwner){user ->
            if (user != null){
                Toast.makeText(requireContext(), "Register Successful", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    override fun setOnClick() {
        binding.registerAccount.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val username = binding.username.text.toString()
            if (binding.password.text.toString() != binding.rePassword.text.toString()){
                Toast.makeText(requireContext(), "Mat khau khong trung khop", Toast.LENGTH_LONG).show()
            }
            else if(email.isEmpty() || password.isEmpty() || username.isEmpty() ||binding.rePassword.text.toString().isEmpty()){
                Toast.makeText(requireContext(), "Yeu cau ban nhap day du thong tin", Toast.LENGTH_LONG).show()
            }
            else{
                viewModel.register(email, password, username)
            }
        }
        binding.loginScreen.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            findNavController().popBackStack()
        }
    }


}