package com.example.expense_management.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expense_management.R
import com.example.expense_management.data.Expense

class ExpenseAdapter(val expenseList: List<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
    val onClick: ((Expense) -> Unit)? = null

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.content)
        val date = itemView.findViewById<TextView>(R.id.date)
        val money = itemView.findViewById<TextView>(R.id.money)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.title.text = expenseList[position].title
        holder.date.text = expenseList[position].date.toString()
        holder.money.text = expenseList[position].price.toString()
        onClick?.invoke(expenseList[position])
    }
}