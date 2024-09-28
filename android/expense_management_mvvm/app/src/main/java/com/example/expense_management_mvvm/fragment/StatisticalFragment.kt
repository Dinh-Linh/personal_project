package com.example.expense_management_mvvm.fragment

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expense_management_mvvm.adapter.StatisticalAdapter
import com.example.expense_management_mvvm.base.BaseFragment
import com.example.expense_management_mvvm.base.BaseViewModel
import com.example.expense_management_mvvm.databinding.FragmentStatisticalBinding


class StatisticalFragment : BaseFragment<FragmentStatisticalBinding>(FragmentStatisticalBinding::inflate){
    override val viewModel: BaseViewModel
        get() = ViewModelProvider(this)[BaseViewModel::class.java]

    private val adapter = StatisticalAdapter()
    override fun initData() {

    }

    override fun bindData() {
        binding.rclView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rclView.adapter = adapter
    }

    override fun observerData() {

    }

    override fun setOnClick() {

    }

}