package com.example.firebaseproject.chat.adapter

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseproject.chat.model.User
import com.example.firebaseproject.chat.view.ChatActivity
import com.example.firebaseproject.databinding.ItemActiveChatUserListBinding

class ActiveChatUserViewHolder(private val binding: ItemActiveChatUserListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: User) {
        binding.activeUserTv.text = item.name
        binding.activeUserTv.setOnClickListener {
            val intent = Intent(binding.root.context,ChatActivity::class.java)
            intent.putExtra("name",item.name)
            intent.putExtra("receiverUId",item.uId)
            binding.root.context.startActivity(intent)
        }
    }
}