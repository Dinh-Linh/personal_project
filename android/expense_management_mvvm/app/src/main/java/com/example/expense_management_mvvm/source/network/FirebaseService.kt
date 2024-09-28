package com.example.expense_management_mvvm.source.network

import android.content.res.Resources.NotFoundException
import android.util.Log
import android.widget.Toast
import com.example.expense_management_mvvm.data.ExpenseManagement
import com.example.expense_management_mvvm.data.User
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Job
import java.time.Month
import java.util.Calendar
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseService {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    suspend fun register(email: String, password: String, username: String): FirebaseUser? {
        return suspendCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val newUser = User(username = username)
                    task.result.user?.let { user ->
                        db.collection("user").document(user.uid).set(newUser)
                            .addOnSuccessListener {
                                continuation.resume(auth.currentUser)
                            }.addOnFailureListener { ex ->
                                continuation.resumeWithException(ex)
                            }
                    }
                } else {
                    task.exception?.let { exception ->
                        continuation.resumeWithException(exception)
                    }

                }
            }.addOnFailureListener { exception ->
                exception.message
            }
        }
    }

    suspend fun login(email: String, password: String): FirebaseUser? {
        return suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(auth.currentUser)
                } else {
                    task.exception?.let { exception ->
                        continuation.resumeWithException(exception)
                    }
                }

            }.addOnFailureListener { exception ->
                exception.message

            }
        }
    }

    suspend fun getUser(userId: String): User? {
        val result =
            suspendCoroutine { continuation ->
                auth.currentUser?.let {
                    db.collection("user").document(userId).get().addOnSuccessListener { document ->
                        if (document != null) {
                            val username = document.toObject(User::class.java)
                            continuation.resume(username)
                        } else {
                            continuation.resumeWithException(NotFoundException())
                        }
                    }.addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
                }
            }
        return result
    }

    suspend fun logout() {
        return suspendCoroutine {
            auth.signOut()
        }
    }

    suspend fun addExpense(
        userId: String,
        title: String,
        date: Timestamp,
        details: String,
        price: Double,
        type: String
    ) {
        return suspendCoroutine { continuation ->
            auth.currentUser?.let {
                val newExpense = ExpenseManagement(
                    title = title,
                    date = date,
                    details = details,
                    price = price,
                    type = type
                )
                db.collection("expense_management").document().set(newExpense)
                    .addOnSuccessListener {
                        continuation.resumeWith(Result.success(Unit))
                    }
                    .addOnFailureListener { exception ->
                        Log.e("FirebaseService", "Failed to add expense: ${exception.message}")
                        continuation.resumeWith(Result.failure(exception))
                    }
            }
        }
    }

    suspend fun getAllItem(
        title: String,
        month: Timestamp
    ): List<ExpenseManagement> {
        return suspendCoroutine { continuation ->
            val calendar = Calendar.getInstance()
            calendar.time = month.toDate()

            //Set current month
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            val startOfMonth = Timestamp(calendar.time)

            //Add next month
            calendar.add(Calendar.MONTH, 1)
            val startOfNextMonth = Timestamp(calendar.time)

            auth.currentUser?.let {
                //Log.d("Query Params", "Title: $title, Start: $startOfMonth, End: $startOfNextMonth")
                Log.d("Query Params", "Title: $title, Start: ${startOfMonth.toDate()}, End: ${startOfNextMonth.toDate()}")
                db.collection("expense_management")
                    .whereEqualTo("title", title)
                    .whereGreaterThan("date", startOfMonth)
                    .whereLessThan("date", startOfNextMonth)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val listItem = querySnapshot.documents.mapNotNull { doc ->
                            doc.toObject(ExpenseManagement::class.java)
                        }
                        continuation.resumeWith(Result.success(listItem))
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWith(Result.failure(exception))
                    }
            }
        }
    }
}