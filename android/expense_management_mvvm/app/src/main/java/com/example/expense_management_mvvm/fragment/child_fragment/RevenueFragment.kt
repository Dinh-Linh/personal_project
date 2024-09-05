package com.example.expense_management_mvvm.fragment.child_fragment

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.expense_management_mvvm.R
import com.example.expense_management_mvvm.base.BaseFragment
import com.example.expense_management_mvvm.base.BaseViewModel
import com.example.expense_management_mvvm.databinding.FragmentRevenueBinding
import com.example.expense_management_mvvm.utils.DateUtils
import com.example.expense_management_mvvm.view_model.HomeViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.util.Date


class RevenueFragment : BaseFragment<FragmentRevenueBinding>(FragmentRevenueBinding::inflate) {
    private lateinit var dateUtils: DateUtils
    override val viewModel: HomeViewModel
        get() = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    private val auth = Firebase.auth

    override fun initData() {
        dateUtils = DateUtils(requireContext())
    }

    override fun bindData() {
        binding.date.text = dateUtils.formatDate()
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

        binding.prev.setOnClickListener {
            binding.date.text = dateUtils.prevDate()
        }
        binding.next.setOnClickListener {
            binding.date.text = dateUtils.nextDate()
        }

        binding.date.setOnClickListener {
            DateUtils(requireContext()).showDatePicker { selectedDate ->
                binding.date.text = selectedDate
            }
        }

        binding.addRevenue.setOnClickListener {
            val date = DateUtils(requireContext()).convertToTimeStamp(binding.date.text.toString())
            val note = binding.note.text.toString()
            val money = binding.money.text.toString()
            val title = binding.category.text.toString()
            val type = "revenue"
            if (title.isEmpty() || note.isEmpty() || money.isEmpty()) {
                Toast.makeText(requireContext(), R.string.add_full_field, Toast.LENGTH_LONG).show()
            } else {
                viewModel.addExpense(
                    auth.currentUser?.uid.toString(),
                    title,
                    date!!,
                    note,
                    money.toDouble(),
                    type
                )
                viewModel.addExpenseResult.observe(viewLifecycleOwner) { result ->
                    if (result.isSuccess) {
                        Toast.makeText(
                            requireContext(),
                            R.string.add_successfully,
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else {
                        Toast.makeText(requireContext(), R.string.add_failure, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }


}