package com.example.firebaseproject.chat.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseproject.chat.model.Message
import com.example.firebaseproject.databinding.ItemMessageBinding
import com.google.firebase.auth.FirebaseAuth

class MessageViewHolder(private val binding: ItemMessageBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Message){
        if(item.senderUId == FirebaseAuth.getInstance().currentUser?.uid){
            binding.sendText.visibility = View.VISIBLE
            binding.sendText.text = item.message
        }else{
            binding.receiveText.visibility = View.VISIBLE
            binding.receiveText.text = item.message
        }
    }
}