package com.example.hotkompoti.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hotkompoti.R
import com.example.hotkompoti.databinding.ActivityLoginBinding
import com.example.hotkompoti.profile.ProfileActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }

        registerListeners()
    }

    private fun registerListeners() {

        binding.loginRegisterButton.setOnClickListener {
            binding.loginRegisterButton.isEnabled = false
            startActivity(Intent(this, RegisterActivity::class.java))
            binding.loginRegisterButton.isEnabled = true
        }

        binding.loginForgotButton.setOnClickListener {
            binding.loginForgotButton.isEnabled = false
            startActivity(Intent(this, ForgotActivity::class.java))
            binding.loginForgotButton.isEnabled = true
        }

        binding.loginButton.setOnClickListener {
            binding.loginButton.isEnabled = false
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                binding.loginEmailContainer.helperText = "Invalid Email or Password"
                binding.loginPasswordContainer.helperText = "Invalid Email or Password"
                binding.loginButton.isEnabled = true
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                } else {
                    binding.loginEmailContainer.helperText = "Invalid Email or Password"
                    binding.loginPasswordContainer.helperText = "Invalid Email or Password"
                    binding.loginButton.isEnabled = true
                }
            }
        }

    }
}