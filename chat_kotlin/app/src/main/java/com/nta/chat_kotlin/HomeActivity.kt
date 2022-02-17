package com.nta.chat_kotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nta.chat_kotlin.adapter.adapterNameHome
import com.nta.chat_kotlin.common.User

class HomeActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var list:ArrayList<User>
    private lateinit var adapter:adapterNameHome
    private lateinit var toolbar:Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mAuth   = FirebaseAuth.getInstance()
        recyclerView = findViewById(R.id.idRCV_Home)
        list = ArrayList()
        reference = FirebaseDatabase.getInstance().getReference()
        recyclerView.setHasFixedSize(true)
        //toolbar
        toolbar = findViewById(R.id.idToolbar_Home)
        setSupportActionBar(toolbar)
        toolbar.title = "Friends"

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = adapterNameHome(this,list)
        recyclerView.adapter = adapter

        reference.child("user").addValueEventListener(object: ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if(!currentUser!!.email!!.equals(mAuth.currentUser!!.email)){
                        list.add(currentUser)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    //menu logout
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout_menu){
            mAuth.signOut()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}