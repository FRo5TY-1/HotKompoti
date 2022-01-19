package com.example.hotkompoti.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.hotkompoti.R
import com.example.hotkompoti.databinding.FragmentInformationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class InformationFragment : Fragment(R.layout.fragment_information) {
    private var fragmentInformationBinding: FragmentInformationBinding? = null
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("Users")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentInformationBinding.bind(view)
        fragmentInformationBinding = binding

        db.child(auth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        db.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                val firstName = it.child("firstName").value
                val lastName = it.child("lastName").value
                val country = it.child("country").value
                val number = it.child("number").value

                binding.informationTextView.text = " First Name: $firstName " +
                        "\nLast Name: $lastName " +
                        "\nCountry of residence: $country " +
                        "\nPhone Number: $number "
            }
        }
    }
}