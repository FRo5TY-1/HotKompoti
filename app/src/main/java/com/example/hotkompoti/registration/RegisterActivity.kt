package com.example.hotkompoti.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.hotkompoti.R
import com.example.hotkompoti.databinding.ActivityRegisterBinding
import com.example.hotkompoti.profile.ProfileActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailFocusListener()
        passwordFocusListener()
        repeatPasswordFocusListener()

        binding.registerActivityButton.setOnClickListener { registration() }
    }

    private fun registration() {
        binding.registerEmailContainer.helperText = emailValidate()
        binding.registerPasswordContainer.helperText = passwordValidate()
        binding.registerRepeatPasswordContainer.helperText = repeatPasswordValidate()

        val emailValid = binding.registerEmailContainer.helperText == null
        val passwordValidate = binding.registerPasswordContainer.helperText == null
        val repeatPasswordValidate = binding.registerRepeatPasswordContainer.helperText == null

        if (emailValid && passwordValidate && repeatPasswordValidate) {
            val email = binding.registerEmail.text.toString()
            val password = binding.registerPassword.text.toString()

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, ProfileActivity::class.java))
                        finish()
                    }
                }
        }
    }


    private fun emailFocusListener() {
        binding.registerEmail.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.registerEmailContainer.helperText = emailValidate()
            }
        }
    }


    private fun emailValidate(): String? {
        val emailText = binding.registerEmail.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            return "Invalid Email Address"
        }
        return null
    }


    private fun passwordFocusListener() {
        binding.registerPassword.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.registerPasswordContainer.helperText = passwordValidate()
            }
        }
    }


    private fun passwordValidate(): String? {
        val password = binding.registerPassword.text.toString()
        return when {
            password.length < 6 -> {
                "Password Must Contain 6 Characters"
            }
            !password.matches(".*[A-Z].*".toRegex()) -> {
                "Password Must Contain 1 Upper-case Character"
            }
            !password.matches(".*[a-z].*".toRegex()) -> {
                "Password Must Contain 1 Lower-case Character"
            }
            !password.matches(".*[0123456789].*".toRegex()) -> {
                "Password Must Contain 1 Number"
            }
            else -> null
        }
    }


    private fun repeatPasswordFocusListener() {
        binding.registerRepeatPassword.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.registerRepeatPasswordContainer.helperText = repeatPasswordValidate()
            }
        }
    }


    private fun  repeatPasswordValidate(): String? {
        val password = binding.registerPassword.text.toString()
        val repeatPassword = binding.registerRepeatPassword.text.toString()

        if (password != repeatPassword) {
            return "Passwords Don't Match"
        }
        return null
    }
}