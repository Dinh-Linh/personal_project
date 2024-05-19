package com.example.expense_management.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.expense_management.databinding.FragmentExpenseBinding
import com.example.expense_management.func.DateUtils



class ExpenseFragment : Fragment() {

    private val bindingFm by lazy { FragmentExpenseBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dateUtils = DateUtils(requireContext())
        bindingFm.date.text = dateUtils.getCurrentDate()
        bindingFm.prev.setOnClickListener{
            bindingFm.date.text = dateUtils.getPreDate()
        }
        bindingFm.next.setOnClickListener{
            bindingFm.date.text = dateUtils.getNextDate()
        }
        bindingFm.date.setOnClickListener{
            dateUtils.showDatePickerDialog {selectedDate ->
                Toast.makeText(requireContext(), "successful", Toast.LENGTH_LONG).show()
                bindingFm.date.text = selectedDate
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