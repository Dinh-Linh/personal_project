package com.example.expense_management_mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.expense_management_mvvm.base.BaseAdapter
import com.example.expense_management_mvvm.base.BaseViewHolder
import com.example.expense_management_mvvm.data.ExpenseManagement
import com.example.expense_management_mvvm.databinding.ItemExpenseBinding
import com.example.expense_management_mvvm.utils.DateUtils

class StatisticalAdapter() : BaseAdapter<ExpenseManagement, ItemExpenseBinding>(ItemExpenseBinding::inflate) {
    override fun bindData(binding: ItemExpenseBinding, item: ExpenseManagement, position: Int) {
        binding.details.text = item.details
        binding.date.text = item.date.toString()
        binding.money.text = item.price.toString()
    }

    override fun onItemClick(binding: ItemExpenseBinding, item: ExpenseManagement, position: Int) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ItemExpenseBinding> {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemExpenseBinding.inflate(inflate, parent, false)
        return BaseViewHolder(binding)
    }
}