package com.example.expense_management_mvvm.fragment

import androidx.lifecycle.ViewModelProvider
import com.example.expense_management_mvvm.base.BaseFragment
import com.example.expense_management_mvvm.base.BaseViewModel
import com.example.expense_management_mvvm.databinding.FragmentDetailsBinding
import com.example.expense_management_mvvm.utils.DateUtils


class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {
    private lateinit var dateUtils: DateUtils
    override val viewModel : BaseViewModel
        get() = ViewModelProvider(this)[BaseViewModel::class.java]

    override fun initData() {
        dateUtils = DateUtils(requireContext())
    }

    override fun bindData() {
        binding.date.text = dateUtils.formatMonth()
    }

    override fun observerData() {

    }

    override fun setOnClick() {
        binding.next.setOnClickListener {
            binding.date.text = dateUtils.nextMonth()
        }
        binding.prev.setOnClickListener {
            binding.date.text = dateUtils.prevMonth()
        }
    }

}