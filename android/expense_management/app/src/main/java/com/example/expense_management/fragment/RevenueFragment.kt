package com.example.expense_management.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.expense_management.data.Revenue
import com.example.expense_management.databinding.FragmentRevenueBinding
import com.example.expense_management.func.ConvertToTimestamp
import com.example.expense_management.func.DateUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


class RevenueFragment : Fragment() {

    private val bindingFm by lazy { FragmentRevenueBinding.inflate(layoutInflater) }
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

        //tiền lương
        bindingFm.salary.setOnClickListener {
            bindingFm.category.text = "Tiền lương"
        }
        //tiền phụ cấp
        bindingFm.allowance.setOnClickListener {
            bindingFm.category.text = "Tiền phụ cấp"
        }
        //tiền thưởng
        bindingFm.bonus.setOnClickListener {
            bindingFm.category.text = "Tiền thưởng"
        }
        //thu nhập phụ
        bindingFm.secondIncome.setOnClickListener {
            bindingFm.category.text = "Thu nhập phụ"
        }
        //Đầu tư
        bindingFm.invest.setOnClickListener {
            bindingFm.category.text = "Đầu tư"
        }
        //Thu nhập tạm
        bindingFm.tempIncome.setOnClickListener {
            bindingFm.category.text = "Thu nhập tạm"
        }

        bindingFm.addRevenue.setOnClickListener{
            val dateTimestamp = ConvertToTimestamp().convertToTimestamp(dateString)
            auth.currentUser.let { revenue ->
                val newRevenue = Revenue(
                    title = bindingFm.category.text.toString(),
                    content = bindingFm.note.text.toString(),
                    date = dateTimestamp,
                    price = bindingFm.tienThu.text.toString().toDouble()
                )
                db.collection("total_revenue").document().set(newRevenue).addOnSuccessListener {
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