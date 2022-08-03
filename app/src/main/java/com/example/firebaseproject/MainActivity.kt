package com.example.firebaseproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.example.firebaseproject.chat.view.ActiveChatListActivity
import com.example.firebaseproject.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var toggle: ActionBarDrawerToggle

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startActivity(Intent(this@MainActivity,ActiveChatListActivity::class.java))

        binding.apply {
           toggle = ActionBarDrawerToggle(this@MainActivity,drawerLayout,R.string.open,R.string.close)
           drawerLayout.addDrawerListener(toggle)
           toggle.syncState()
           supportActionBar?.title="Chat App"
           supportActionBar?.setDisplayHomeAsUpEnabled(true)

           navView.setNavigationItemSelectedListener {
               when(it.itemId){
                   R.id.first_item ->{
                       drawerLayout.closeDrawers()
                       startActivity(Intent(this@MainActivity,ActiveChatListActivity::class.java))
                   }
               }
               true
           }
       }
        mAuth = FirebaseAuth.getInstance()
        setNavigationDrawer()
        val email = intent.getStringExtra("email")
        binding.textview.text = "Welcome \n$email"
        binding.signOutBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Exit")
            builder.setMessage("Do you really want to sign out")
            builder.setPositiveButton("yes"){ _,_ ->
                binding.progress.visibility = View.VISIBLE
                mAuth.signOut()
                startActivity(Intent(this, SignInActivity::class.java))
            }
            builder.setNegativeButton("N0"){ dialog,_ ->
                dialog.dismiss()
            }
            builder.show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setNavigationDrawer() {
        binding.navView.getHeaderView(0).findViewById<AppCompatTextView>(R.id.header_title).text = "Welcome\n${mAuth.currentUser?.email}"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) true
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser != null) {
            binding.textview.text = "Welcome\n"+mAuth.currentUser?.email
        }
    }


}