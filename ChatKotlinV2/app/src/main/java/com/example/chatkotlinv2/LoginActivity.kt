package com.example.chatkotlinv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.chatkotlinv2.databinding.ActivityLoginBinding
import com.example.chatkotlinv2.viewmodel.LoginActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var auth:FirebaseAuth
    private val TAG = "login"
    private val viewModel by lazy {
        ViewModelProvider(this).get(LoginActivityViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //init viewmodel
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        //init firebase auth
        auth = Firebase.auth

        viewModel.clickLogin = {
            val email = viewModel.email.value.toString()
            val password = viewModel.password.value.toString()

            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) {
                if (it.isSuccessful){
                    Log.d(TAG, "onCreate: login success")
                    val user = auth.currentUser
                    updateUI(user)
                }else {
                    Toast.makeText(this,"username or password wrong!",Toast.LENGTH_LONG).show()
                    Log.d(TAG, "onCreate: login fail")
                    updateUI(null)
                }
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user != null){
            startIntent(this,SetUserActivity.Instance())
        }
    }

    companion object{
        fun Instance() = LoginActivity()
    }
}