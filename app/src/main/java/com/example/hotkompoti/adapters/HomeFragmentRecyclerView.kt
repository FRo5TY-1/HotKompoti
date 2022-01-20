package com.example.hotkompoti.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotkompoti.R
import com.example.hotkompoti.data.UserInfo
import com.google.firebase.storage.FirebaseStorage

class HomeFragmentRecyclerView(private val personList:ArrayList<UserInfo>): RecyclerView.Adapter<HomeFragmentRecyclerView.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentRecyclerView.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: HomeFragmentRecyclerView.MyViewHolder, position: Int) {
        val currentItem = personList[position]
        val fileName = currentItem.pictureUrl

        val ref = FirebaseStorage.getInstance().getReference("/images/${fileName}")
        ref.downloadUrl.addOnSuccessListener {
            Glide
                .with(holder.fileName.context)
                .load(it)
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(holder.fileName)
        }
        holder.personName.text = "${currentItem.firstName} ${currentItem.lastName}"
        holder.personFrom.text = "${currentItem.country}"
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val personName : TextView = itemView.findViewById(R.id.personName)
        val personFrom: TextView = itemView.findViewById(R.id.personFrom)
        val fileName : ImageView = itemView.findViewById(R.id.personProfilePicture)
    }
}