package com.example.expense_management_mvvm.fragment.child_fragment

import androidx.lifecycle.ViewModelProvider
import com.example.expense_management_mvvm.R
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
        val buttons = listOf(
            binding.eat,
            binding.shopping,
            binding.clothes,
            binding.friendship,
            binding.health,
            binding.studying,
            binding.travel,
            binding.phone,
            binding.house
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                val categoryText = when(it.id){
                    R.id.eat -> R.string.eat
                    R.id.shopping -> R.string.shopping
                    R.id.clothes -> R.string.clothes
                    R.id.friendship -> R.string.friendship
                    R.id.health -> R.string.health
                    R.id.studying -> R.string.study
                    R.id.travel -> R.string.travel
                    R.id.phone -> R.string.phoning
                    R.id.house -> R.string.house
                    else -> null
                }
                categoryText?.let { text ->
                    binding.category.text = getString(text)
                }
            }
        }
    }

}