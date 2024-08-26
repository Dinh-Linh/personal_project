package com.example.expense_management_mvvm.source.network

import com.example.expense_management_mvvm.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
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
        return suspendCoroutine { continuation ->
            auth.currentUser?.let {
                db.collection("user").document(userId).get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null && document.exists()) {
                            val username = document.toObject<User>()
                            continuation.resume(username)
                        }
                    }
                }.addOnFailureListener { exception ->
                    exception.message
                }
            }
        }
    }


}