package com.example.expense_management_mvvm.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.expense_management_mvvm.base.BaseViewModel
import com.example.expense_management_mvvm.data.ExpenseManagement
import com.example.expense_management_mvvm.repository.DetailRepository
import com.example.expense_management_mvvm.source.network.FirebaseService
import com.google.firebase.Timestamp

class DetailViewModel : BaseViewModel() {
    private val detailRepository = DetailRepository(FirebaseService())

    private val _listItem = MutableLiveData<List<ExpenseManagement>>()
    private val listItem: LiveData<List<ExpenseManagement>> get() = _listItem

    fun getItem(title: String, month: Timestamp) {
        executeTask(
            request = {
                detailRepository.getItem(title, month)
            },
            onSuccess = { results ->
                _listItem.value = results
                Log.d("List item", results.toString())
            },
            onError = { exception ->
                Log.e("Exception: ", exception.message.toString())
            }
        )
    }
}