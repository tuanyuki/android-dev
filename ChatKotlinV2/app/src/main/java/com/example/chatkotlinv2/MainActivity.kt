package com.example.chatkotlinv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatkotlinv2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
    companion object{
        fun Instance() = MainActivity()
    }
}