package com.example.expense_management_mvvm.fragment

import androidx.lifecycle.ViewModelProvider
import com.example.expense_management_mvvm.base.BaseFragment
import com.example.expense_management_mvvm.base.BaseViewModel
import com.example.expense_management_mvvm.databinding.FragmentDetailsBinding
import com.example.expense_management_mvvm.utils.DateUtils
import com.example.expense_management_mvvm.view_model.DetailViewModel
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth


class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {
    private lateinit var dateUtils: DateUtils
    private lateinit var convertMonthToTimestamp: Timestamp
    override val viewModel: DetailViewModel
        get() = ViewModelProvider(this)[DetailViewModel::class.java]

    override fun initData() {
        dateUtils = DateUtils(requireContext())
    }

    override fun bindData() {
        binding.date.text = dateUtils.formatMonth()
    }

    override fun observerData() {

    }

    override fun setOnClick() {
        val events = listOf(
            Pair(binding.bonus, binding.bonusName),
            Pair(binding.salary, binding.salaryName),
            Pair(binding.allowance, binding.allowanceName),
            Pair(binding.secondSalary, binding.secondSalaryName),
            Pair(binding.tempSalary, binding.tempSalaryName),
            Pair(binding.invest, binding.investName),
            Pair(binding.eat, binding.eatName),
            Pair(binding.shopping, binding.shoppingName),
            Pair(binding.clothes, binding.clothesName),
            Pair(binding.relationship, binding.relationshipName),
            Pair(binding.health, binding.healthName),
            Pair(binding.study, binding.studyName),
            Pair(binding.travelling, binding.travellingName),
            Pair(binding.phone, binding.phoneName),
            Pair(binding.house, binding.houseName),
        )

        for (i in events.indices) {
            val (button, nameOfButton) = events[i]
            button.setOnClickListener {
                convertMonthToTimestamp = dateUtils.convertMonthToTimestamp(binding.date.text.toString())!!
                viewModel.getItem(nameOfButton.text.toString(), convertMonthToTimestamp)
            }
        }
        binding.next.setOnClickListener {
            binding.date.text = dateUtils.nextMonth()
        }
        binding.prev.setOnClickListener {
            binding.date.text = dateUtils.prevMonth()
        }
    }

}