package com.example.hotkompoti.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.hotkompoti.R
import com.example.hotkompoti.databinding.FragmentMenuBinding
import com.example.hotkompoti.registration.ChangePasswordActivity
import com.example.hotkompoti.registration.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class MenuFragment : Fragment(R.layout.fragment_menu) {
    private var fragmentMenuBinding: FragmentMenuBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMenuBinding.bind(view)
        fragmentMenuBinding = binding

        binding.menuChangePassword.setOnClickListener {
            activity?.let{
                val intent = Intent (it, ChangePasswordActivity::class.java)
                it.startActivity(intent)
            }
        }
        binding.menuLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            activity?.let{
                val intent = Intent (it, LoginActivity::class.java)
                it.startActivity(intent)
            }
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        fragmentMenuBinding = null
        super.onDestroyView()
    }
}