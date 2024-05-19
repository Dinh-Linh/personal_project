package com.example.expense_management.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.expense_management.R
import com.example.expense_management.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val auth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.loginScreen.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.register.setOnClickListener{
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val rePassword = binding.rePassword.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty() && rePassword.isNotEmpty()){
                if(password == rePassword){
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {task ->
                            if (task.isSuccessful){
                                Toast.makeText(this, "Creat account Successful", Toast.LENGTH_LONG).show()
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                            else{
                                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                            }
                        }
                        .addOnFailureListener{exception ->
                            Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
                        }
                }
            }
        }
    }
}