package com.example.expense_management_mvvm.source.network

import android.content.res.Resources.NotFoundException
import com.example.expense_management_mvvm.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Job
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

    val getUserJob: Job? = null

    suspend fun getUser(userId: String): User {
        val result =
            suspendCoroutine { continuation ->
                auth.currentUser?.let {
                    db.collection("user").document(userId).get().addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            val username = User(document.data?.get("username").toString())
                            continuation.resume(username)
                        }
                        else{
                            continuation.resumeWithException(NotFoundException())
                        }
                    }.addOnFailureListener { exception ->
                       continuation.resumeWithException(exception)
                    }
                }
            }
        return result
    }
}