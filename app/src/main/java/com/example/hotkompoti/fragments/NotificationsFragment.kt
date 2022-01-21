package com.example.hotkompoti.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.example.hotkompoti.R
import com.example.hotkompoti.data.MessageInfo
import com.example.hotkompoti.data.UserInfo
import com.example.hotkompoti.profile.ChatActivity
import com.example.hotkompoti.profile.ChatReceivedItem
import com.example.hotkompoti.profile.ChatSentItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.chat_sent_row.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.notification_item.view.*
import kotlinx.android.synthetic.main.person_item.view.*

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    val adapter = GroupAdapter<ViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenForMessages()

        NotificationsRecyclerView.adapter = adapter
    }

    private fun listenForMessages() {
        val ref = FirebaseDatabase.getInstance().getReference("/messages")

        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(MessageInfo::class.java)
                val fromId = chatMessage?.fromId
                val toId = chatMessage?.toId
                val db = FirebaseDatabase.getInstance().getReference("Users")
                db.child(toId!!).get().addOnSuccessListener {
                    if (it.exists()) {
                        val firstName = it.child("firstName").value
                        val lastName = it.child("lastName").value
                        val name = "$firstName $lastName"

                        if (chatMessage != null) {
                            if (fromId == FirebaseAuth.getInstance().uid) {
                                adapter.add(NotificationItem(name, chatMessage.text))
                            }
                        }
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }
}

class NotificationItem(val name: String, val text: String): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.notificationTitle.text = "You have a new message from $name"
        viewHolder.itemView.notificationDescription.text = text
    }

    override fun getLayout(): Int {
        return R.layout.notification_item
    }
}