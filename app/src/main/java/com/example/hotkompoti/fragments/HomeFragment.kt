package com.example.hotkompoti.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.example.hotkompoti.R
import com.example.hotkompoti.data.UserInfo
import com.example.hotkompoti.profile.ChatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.person_item.view.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchUsers()

        setHasOptionsMenu(true)
    }

    private fun fetchUsers() {
        val db = FirebaseDatabase.getInstance().getReference("Users")
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()
                snapshot.children.forEach {
                    val userInfo = it.getValue(UserInfo::class.java)
                    if (userInfo != null) {
                        adapter.add(UserItem(userInfo))
                    }
                }

                adapter.setOnItemClickListener { item, view ->

                    val userItem = item as UserItem

                    val intent = Intent(view.context, ChatActivity::class.java)
                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)
                }

                HomeFragmentRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    companion object {
        val USER_KEY = "USER_KEY"
    }

    class UserItem(val user: UserInfo) : Item<ViewHolder>() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            val ref = FirebaseStorage.getInstance().getReference("/images/${user.pictureUrl}")

            ref.downloadUrl.addOnSuccessListener { it1 ->
                Glide
                    .with(viewHolder.itemView.personProfilePicture.context)
                    .load(it1)
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .into(viewHolder.itemView.personProfilePicture)
            }

            viewHolder.itemView.personName.text = "${user.firstName} ${user.lastName}"
            viewHolder.itemView.personFrom.text = user.country
        }

        override fun getLayout(): Int {
            return R.layout.person_item
        }
    }
}