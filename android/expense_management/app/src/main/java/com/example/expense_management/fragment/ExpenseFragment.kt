package com.example.expense_management.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        bindingFm.prev.setOnClickListener{
            bindingFm.date.text = dateUtils.getPreDate()
        }
        bindingFm.next.setOnClickListener{
            bindingFm.date.text = dateUtils.getNextDate()
        }
        bindingFm.date.setOnClickListener{
            dateUtils.showDatePickerDialog {selectedDate ->
                Toast.makeText(requireContext(), "successful", Toast.LENGTH_LONG).show()
                dateString = selectedDate
                bindingFm.date.text = dateString
            }
        }


        bindingFm.food.setOnClickListener{
            bindingFm.category.text = "Ăn uống"
        }
        bindingFm.shop.setOnClickListener{
            bindingFm.category.text = "Chi tiêu hàng"
        }
        bindingFm.clothes.setOnClickListener{
            bindingFm.category.text = "Quần áo"
        }
        bindingFm.relationship.setOnClickListener{
            bindingFm.category.text = "Phí giao lưu"
        }
        bindingFm.health.setOnClickListener{
            bindingFm.category.text = "Sức khoẻ"
        }
        bindingFm.study.setOnClickListener{
            bindingFm.category.text = "Học tập"
        }
        bindingFm.travel.setOnClickListener{
            bindingFm.category.text = "Đi lại"
        }
        bindingFm.call.setOnClickListener{
            bindingFm.category.text = "Liên lạc"
        }
        bindingFm.house.setOnClickListener{
            bindingFm.category.text = "Nhà cửa"
        }


        bindingFm.addExpense.setOnClickListener{
            val dateTimestamp = ConvertToTimestamp().convertToTimestamp(dateString)
            auth.currentUser.let { revenue ->
                val newExpense = Expense(
                    title = bindingFm.category.text.toString(),
                    content = bindingFm.note.text.toString(),
                    date = dateTimestamp,
                    price = bindingFm.tienChi.text.toString().toDouble()
                )
                db.collection("total_expense").document().set(newExpense).addOnSuccessListener {
                    Toast.makeText(requireContext(), "add successful", Toast.LENGTH_LONG).show()
                }
                    .addOnFailureListener{exception ->
                        Toast.makeText(requireContext(), exception.message, Toast.LENGTH_LONG).show()
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