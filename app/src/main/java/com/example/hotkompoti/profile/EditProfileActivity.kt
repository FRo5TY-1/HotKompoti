package com.example.hotkompoti.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.hotkompoti.R
import com.example.hotkompoti.data.UserInfo
import com.example.hotkompoti.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private var editProfilePhotoUri: Uri? = null
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db.child(auth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        focusListeners()
        onClickListeners()

    }

    private fun onClickListeners() {

        binding.chooseImageButton.setOnClickListener {
            selectImageFromGallery()
        }

        binding.editProfileFinish.setOnClickListener {

            if (dataLoad()) {
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
        }

    }

    private fun selectImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            editProfilePhotoUri = data?.data!!
            binding.editProfilePicture.setImageURI(editProfilePhotoUri)
        }
    }

    private fun dataLoad(): Boolean {

        val firstNameValidate = binding.firstNameContainer.helperText == null
        val lastNameValidate = binding.lastNameContainer.helperText == null
        val countryValidate = binding.countryContainer.helperText == null
        val numberValidate = binding.numberContainer.helperText == null

        if (firstNameValidate && lastNameValidate && countryValidate && numberValidate) {
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/images/${filename}")

            if(editProfilePhotoUri != null) {
                ref.putFile(editProfilePhotoUri!!)
            }

            val uid = auth.uid.toString()
            val firstName = binding.firstNameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()
            val country = binding.countryEditText.text.toString()
            val number = binding.numberEditText.text.toString()
            val userInfo = UserInfo(uid, filename, firstName, lastName, country, number)
            db.child(auth.currentUser?.uid!!).setValue(userInfo)

            return true
        }
        return false
    }

    private fun focusListeners() {
        binding.firstNameEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.firstNameContainer.helperText = firstNameValidate()
            }
        }
        binding.lastNameEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.lastNameContainer.helperText = lastNameValidate()
            }
        }
        binding.countryEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.countryContainer.helperText = countryValidate()
            }
        }
        binding.numberEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.numberContainer.helperText = numberValidate()
            }
        }
    }

    private fun firstNameValidate() : String? {
        val firstName = binding.firstNameEditText.text.toString()
        if (firstName.length < 3) {
            return "Must Be At Least 3 Characters Long"
        }
        return null
    }
    private fun lastNameValidate() : String? {
        val lastName = binding.lastNameEditText.text.toString()

        if (lastName.length < 3) {
            return "Must Be At Least 3 Characters Long"
        }
        return null
    }
    private fun countryValidate() : String? {
        val country = binding.lastNameEditText.text.toString()

        if (country.length < 3) {
            return "Must Be At Least 3 Characters Long"
        }
        return null
    }
    private fun numberValidate() : String? {
        val lastName = binding.lastNameEditText.text.toString()

        if (lastName.length < 3) {
            return "Must Be At Least 6 numbers"
        }
        return null
    }

}