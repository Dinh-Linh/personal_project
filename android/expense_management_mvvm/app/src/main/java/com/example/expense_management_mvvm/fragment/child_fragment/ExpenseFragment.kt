package com.example.expense_management_mvvm.fragment.child_fragment

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.expense_management_mvvm.R
import com.example.expense_management_mvvm.base.BaseFragment
import com.example.expense_management_mvvm.databinding.FragmentExpenseBinding
import com.example.expense_management_mvvm.utils.DateUtils
import com.example.expense_management_mvvm.view_model.HomeViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class ExpenseFragment : BaseFragment<FragmentExpenseBinding>(FragmentExpenseBinding::inflate) {
    private lateinit var dateUtils: DateUtils
    override val viewModel: HomeViewModel
        get() = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    private val auth = Firebase.auth
    private val db = Firebase.firestore
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
                val categoryText = when (it.id) {
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

        binding.addExpense.setOnClickListener {
            val date = DateUtils(requireContext()).convertToTimeStamp(binding.date.text.toString())
            val note = binding.note.text.toString()
            val money = binding.money.text.toString()
            val title = binding.category.text.toString()
            val type = "expense"
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