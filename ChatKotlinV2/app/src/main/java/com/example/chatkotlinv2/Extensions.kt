package com.example.chatkotlinv2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

fun Activity.startIntent(T: AppCompatActivity, V:AppCompatActivity){
    val intent = Intent(T,V::class.java)
    T.startActivity(intent)
}
