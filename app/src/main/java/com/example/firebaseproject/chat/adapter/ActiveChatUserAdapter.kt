package com.example.firebaseproject.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.firebaseproject.chat.model.User
import com.example.firebaseproject.databinding.ItemActiveChatUserListBinding

class ActiveChatUserAdapter :
    ListAdapter<User, ActiveChatUserViewHolder>(ActiveChatUserDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveChatUserViewHolder {
        return ActiveChatUserViewHolder(
            ItemActiveChatUserListBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ActiveChatUserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}