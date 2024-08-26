package com.example.expense_management_mvvm.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.expense_management_mvvm.R
import com.example.expense_management_mvvm.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.truncate

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        Handler(Looper.getMainLooper()).postDelayed(
            {
                if (isAdded) {
                    val navOption =
                        NavOptions.Builder().setPopUpTo(R.id.splashScreenFragment, true).build()
                    if (currentUser != null) {
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                        requireActivity().finish()
                    } else {
                        findNavController().navigate(
                            R.id.action_splashScreenFragment_to_loginFragment, null, navOption
                        )
                    }
                }
            }, 2000
        )
    }
}