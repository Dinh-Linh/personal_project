package com.example.expense_management_mvvm.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRepository {
    suspend fun <T> getResults(request: suspend CoroutineScope.() -> T): DataState<T> {
        return withContext(Dispatchers.IO) {
            try {
                DataState.Success(request())
            } catch (e: Exception) {
                DataState.Error(e)
            }
        }
    }
}