package com.example.expense_management.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.expense_management.R
import com.example.expense_management.data.Expense
import com.example.expense_management.databinding.FragmentExpenseBinding
import com.example.expense_management.func.ConvertToTimestamp
import com.example.expense_management.func.DateUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


class ExpenseFragment : Fragment() {

    private val bindingFm by lazy { FragmentExpenseBinding.inflate(layoutInflater) }
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dateUtils = DateUtils(requireContext())
        var dateString = bindingFm.date.text.toString()
        dateString = dateUtils.getCurrentDate()
        bindingFm.date.text = dateString
        bindingFm.prev.setOnClickListener {
            bindingFm.date.text = dateUtils.getPreDate()
        }
        bindingFm.next.setOnClickListener {
            bindingFm.date.text = dateUtils.getNextDate()
        }
        bindingFm.date.setOnClickListener {
            dateUtils.showDatePickerDialog { selectedDate ->
                Toast.makeText(requireContext(), "successful", Toast.LENGTH_LONG).show()
                dateString = selectedDate
                bindingFm.date.text = dateString
            }
        }

        val buttons = listOf(
            bindingFm.food,
            bindingFm.shop,
            bindingFm.clothes,
            bindingFm.relationship,
            bindingFm.health,
            bindingFm.study,
            bindingFm.travel,
            bindingFm.call,
            bindingFm.house
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                val categoryText = when (it.id) {
                    R.id.food -> "Ăn uống"
                    R.id.shop -> "Chi tiêu hàng"
                    R.id.clothes -> "Quần áo"
                    R.id.relationship -> "Phí giao lưu"
                    R.id.health -> "Sức khoẻ"
                    R.id.study -> "Học tập"
                    R.id.travel -> "Đi lại"
                    R.id.call -> "Liên lạc"
                    R.id.house -> "Nhà cửa"
                    else -> ""
                }
                bindingFm.category.text = categoryText
            }
        }

        bindingFm.addExpense.setOnClickListener {
            val dateTimestamp = ConvertToTimestamp().convertToTimestamp(dateString)
            auth.currentUser.let {
                val newExpense = Expense(
                    title = bindingFm.category.text.toString(),
                    content = bindingFm.note.text.toString(),
                    date = dateTimestamp,
                    price = bindingFm.tienChi.text.toString().toDouble()
                )
                db.collection("total_expense").document().set(newExpense).addOnSuccessListener {
                    Toast.makeText(requireContext(), "add successful", Toast.LENGTH_LONG).show()
                }
                    .addOnFailureListener { exception ->
                        Toast.makeText(requireContext(), exception.message, Toast.LENGTH_LONG)
                            .show()
                    }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingFm.root
    }

}