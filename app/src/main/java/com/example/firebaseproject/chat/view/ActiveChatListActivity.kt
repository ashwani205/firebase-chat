package com.example.firebaseproject.chat.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseproject.chat.adapter.ActiveChatUserAdapter
import com.example.firebaseproject.chat.model.User
import com.example.firebaseproject.databinding.ActivityActiveChatListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ActiveChatListActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityActiveChatListBinding
    private lateinit var mReference: DatabaseReference
    private lateinit var adapter: ActiveChatUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityActiveChatListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.progressBar.visibility = View.VISIBLE
        supportActionBar?.title="Users"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setActiveChatListAdapter()
    }

    private fun setActiveChatListAdapter() {
        val userList = ArrayList<User>()
        adapter = ActiveChatUserAdapter()
        mBinding.activeChatListRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mBinding.activeChatListRv.adapter = adapter
        adapter.submitList(userList)
        mReference = FirebaseDatabase.getInstance().reference
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        mReference.child("user").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                snapshot.children.forEach {
                    val activeUser = it.getValue(User::class.java)
                    if (activeUser?.uId != currentUserId)
                    userList.add(activeUser!!)
                }
                mBinding.progressBar.visibility = View.GONE
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                mBinding.progressBar.visibility = View.VISIBLE
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}