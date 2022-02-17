package com.nta.chat_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Intro_Activity : AppCompatActivity() {
    private lateinit var btnSignIn:Button
    private lateinit var btnSignUp:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        btnSignIn = findViewById(R.id.idBTNsignin_Intro)
        btnSignUp = findViewById(R.id.idBTNsignup_Intro)
        val intent = Intent(this,Login_Activity::class.java)
        btnSignIn.setOnClickListener {
            startActivity(intent)
        }
        btnSignUp.setOnClickListener { startActivity(Intent(this,SignUp_Activity::class.java)) }
    }
}