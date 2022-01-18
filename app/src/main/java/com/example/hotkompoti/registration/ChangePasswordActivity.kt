package com.example.hotkompoti.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hotkompoti.R
import com.example.hotkompoti.databinding.ActivityChangePasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        focusListeners()

        binding.changeFinishButton.setOnClickListener { passwordChange() }

    }

    private fun passwordChange() {
        binding.changePasswordContainer.helperText = passwordValidate()
        binding.changeRepeatPasswordContainer.helperText = repeatPasswordValidate()

        val passwordValidate = binding.changePasswordContainer.helperText == null
        val repeatPasswordValidate = binding.changeRepeatPasswordContainer.helperText == null

        if (passwordValidate && repeatPasswordValidate) {
            val password = binding.changePassword.text.toString()

            FirebaseAuth.getInstance().currentUser?.updatePassword(password)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }
        }
    }

    private fun focusListeners() {
        binding.changePassword.setOnFocusChangeListener { _, focused ->
            if(!focused) {
                binding.changePasswordContainer.helperText = passwordValidate()
            }
        }

        binding.changeRepeatPassword.setOnFocusChangeListener { _, focused ->
            if(!focused) {
                binding.changeRepeatPasswordContainer.helperText = repeatPasswordValidate()
            }
        }
    }

    private fun passwordValidate(): String? {
        val password = binding.changePassword.text.toString()
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

    private fun  repeatPasswordValidate(): String? {
        val password = binding.changePassword.text.toString()
        val repeatPassword = binding.changeRepeatPassword.text.toString()

        if (password != repeatPassword) {
            return "Passwords Don't Match"
        }
        return null
    }
}