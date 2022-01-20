package com.example.hotkompoti.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.hotkompoti.R
import com.example.hotkompoti.adapters.ProfileViewPagerAdapter
import com.example.hotkompoti.databinding.FragmentProfileBinding
import com.example.hotkompoti.profile.EditProfileActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var fragmentProfileBinding: FragmentProfileBinding
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPagerFragmentsAdapter: ProfileViewPagerAdapter
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("Users")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentProfileBinding.bind(view)
        fragmentProfileBinding = binding

        viewPager2 = binding.profileViewPager
        tabLayout = binding.profileTabLayout
        viewPagerFragmentsAdapter = ProfileViewPagerAdapter(this)
        viewPager2.adapter = viewPagerFragmentsAdapter

        db.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                val firstName = it.child("firstName").value
                val lastName = it.child("lastName").value
                val fileName = it.child("pictureUrl").value
                val ref = FirebaseStorage.getInstance().getReference("/images/${fileName}")

                ref.downloadUrl.addOnSuccessListener { it1 ->
                    Glide
                        .with(this)
                        .load(it1)
                        .centerCrop()
                        .placeholder(R.drawable.ic_baseline_person_24)
                        .into(binding.profilePictureView)
                }

                binding.profilePersonName.text = "$firstName $lastName"
            }
        }

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = "Tab ${position + 1}"
        }.attach()

        binding.editProfileButton.setOnClickListener {
            activity?.let{
                val intent = Intent (it, EditProfileActivity::class.java)
                it.startActivity(intent)
            }
        }
    }
}