package com.example.hotkompoti.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.hotkompoti.R
import com.example.hotkompoti.databinding.ActivityForgotBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        binding = ActivityForgotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendResetButton.setOnClickListener {
            binding.sendResetButton.isEnabled = false
            binding.forgotEmailContainer.helperText = emailValidate()
            val emailValid = binding.forgotEmailContainer.helperText == null
            val email = binding.forgotEmail.text.toString()

            if (emailValid) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
            }

            binding.sendResetButton.isEnabled = true
        }
    }


    private fun emailValidate(): String? {
        val emailText = binding.forgotEmail.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address"
        }
        return null
    }
}