package com.example.hotkompoti.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.hotkompoti.R
import com.example.hotkompoti.databinding.FragmentMenuBinding
import com.example.hotkompoti.databinding.FragmentProfileBinding
import com.example.hotkompoti.profile.EditProfileActivity
import com.example.hotkompoti.registration.ChangePasswordActivity

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var fragmentProfileBinding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentProfileBinding.bind(view)
        fragmentProfileBinding = binding

        binding.editProfileButton.setOnClickListener {
            activity?.let{
                val intent = Intent (it, EditProfileActivity::class.java)
                it.startActivity(intent)
            }
        }
    }
}