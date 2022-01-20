package com.example.hotkompoti.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotkompoti.R
import com.example.hotkompoti.adapters.HomeFragmentRecyclerView
import com.example.hotkompoti.data.UserInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var homeFragmentRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<UserInfo>
    private val db = FirebaseDatabase.getInstance().getReference("Users")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        homeFragmentRecyclerView = view.findViewById(R.id.storeRecyclerView)
        homeFragmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        homeFragmentRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        getProductData()
    }

    private fun getProductData() {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (productsSnapshot in snapshot.children){
                        val product = productsSnapshot.getValue(UserInfo::class.java)
                        userArrayList.add(product!!)
                    }

                    homeFragmentRecyclerView.adapter = HomeFragmentRecyclerView(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}