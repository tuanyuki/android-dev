package com.nta.chat_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nta.chat_kotlin.adapter.adapterChat
import com.nta.chat_kotlin.common.Messages

class ChatActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var txtMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var reference: DatabaseReference
    private lateinit var adapter:adapterChat
    private lateinit var list:ArrayList<Messages>
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        //binding
        txtMessage  = findViewById(R.id.idEDTmessage_Chat)
        btnSend     = findViewById(R.id.idBTNsend_Chat)
        val intent: Intent = getIntent()
        val toolbar = findViewById(R.id.idToolbar_chat) as Toolbar?
        recyclerView = findViewById(R.id.idRCV_Chat)
        mAuth = FirebaseAuth.getInstance()
        //init firebase
        reference = FirebaseDatabase.getInstance().getReference()
        //toolbar
        setSupportActionBar(toolbar)
        toolbar?.title = intent.extras?.get("username").toString()
        //send uid and received uid
        val uidSend:String = intent.extras?.get("uid").toString()
        val sendRoom:String     = mAuth.uid.toString() + uidSend
        val receivedRoom:String = uidSend + mAuth.uid.toString()
        //adapter messages
        list = ArrayList()
        adapter = adapterChat(this,list)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        reference.child("chats").child(sendRoom).child("Messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for(snap in snapshot.children){
                        val mess: Messages? = snap.getValue(Messages::class.java)
                        list.add(mess!!)
                    }
                    adapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        //push messages to database
        btnSend.setOnClickListener({
            if(txtMessage.text.isEmpty()){
                Toast.makeText(this,"pls input your message",Toast.LENGTH_SHORT).show()
            }else{
                val message: Messages = Messages(txtMessage.text.toString(),mAuth.uid)
                reference.child("chats").child(sendRoom).child("Messages").push()
                    .setValue(message)
                reference.child("chats").child(receivedRoom).child("Messages").push()
                    .setValue(message)
            }
            txtMessage.text.clear()
        })
    }
}