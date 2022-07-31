package com.example.firebaseproject.chat.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseproject.R
import com.example.firebaseproject.chat.adapter.MessageAdapter
import com.example.firebaseproject.chat.model.Message
import com.example.firebaseproject.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityChatBinding
    private lateinit var mRef: DatabaseReference
    private lateinit var senderRoom: String
    private lateinit var receiverRoom: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val name = intent.getStringExtra("name")
        val receiverUId = intent.getStringExtra("receiverUId")
        val senderUId = FirebaseAuth.getInstance().currentUser?.uid
        senderRoom = senderUId + receiverUId
        receiverRoom = receiverUId + senderUId
        supportActionBar?.title = name
        mRef = FirebaseDatabase.getInstance().reference

        val messageList = ArrayList<Message>()
        val adapter = MessageAdapter()
        mBinding.messageRv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        mBinding.messageRv.adapter = adapter
        adapter.submitList(messageList)

        //logic for adding data to recyclerview
        mRef.child("chats").child(senderRoom).child("message").addValueEventListener(object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                snapshot.children.forEach { dataSnapshot ->
                    val  message = dataSnapshot.getValue(Message::class.java)
                    message?.let { messageList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        mBinding.sendBtn.setOnClickListener {
            if(mBinding.messageEt.text?.isNotEmpty() == true) {
                val message = Message(mBinding.messageEt.text.toString(), senderUId)
                mRef.child("chats").child(senderRoom).child("message").push()
                    .setValue(message).addOnSuccessListener {
                        mRef.child("chats").child(receiverRoom).child("message").push()
                            .setValue(message)
                    }
                mBinding.messageEt.setText("")
            }
        }
    }
}