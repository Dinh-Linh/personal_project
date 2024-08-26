package com.example.expense_management_mvvm.fragment.child_fragment

import androidx.lifecycle.ViewModelProvider
import com.example.expense_management_mvvm.base.BaseFragment
import com.example.expense_management_mvvm.base.BaseViewModel
import com.example.expense_management_mvvm.databinding.FragmentExpenseBinding

class ExpenseFragment : BaseFragment<FragmentExpenseBinding>(FragmentExpenseBinding::inflate) {
    override val viewModel : BaseViewModel
        get() = ViewModelProvider(requireActivity())[BaseViewModel::class.java]

    override fun initData() {

    }

    override fun bindData() {

    }

    override fun observerData() {

    }

    override fun setOnClick() {

    }

}