package com.example.expense_management_mvvm.fragment.child_fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.expense_management_mvvm.R
import com.example.expense_management_mvvm.base.BaseFragment
import com.example.expense_management_mvvm.base.BaseViewModel
import com.example.expense_management_mvvm.databinding.FragmentRevenueBinding


class RevenueFragment : BaseFragment<FragmentRevenueBinding>(FragmentRevenueBinding::inflate) {
    override val viewModel: BaseViewModel
        get() = ViewModelProvider(requireActivity())[BaseViewModel::class.java]

    override fun initData() {

    }

    override fun bindData() {

    }

    override fun observerData() {

    }

    override fun setOnClick() {
        val buttons = listOf(
            binding.moneyLv1,
            binding.moneyLv2,
            binding.moneyLv3,
            binding.moneyTemp,
            binding.bonus,
            binding.invest
        )
        buttons.forEach { button ->
            button.setOnClickListener {
                val categoryText = when (it.id) {
                    R.id.moneyLv1 -> R.string.money_lv1
                    R.id.moneyLv2 -> R.string.money_lv2
                    R.id.moneyLv3 -> R.string.money_lv3
                    R.id.moneyTemp -> R.string.money_temp
                    R.id.invest -> R.string.invest
                    R.id.bonus -> R.string.bonus
                    else -> null
                }
                categoryText?.let { text ->
                    binding.category.text = getString(text)
                }
            }
        }

    }


}