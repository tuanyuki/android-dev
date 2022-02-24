package com.example.chatkotlinv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.chatkotlinv2.databinding.ActivityRegisterBinding
import com.example.chatkotlinv2.viewmodel.RegisterActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding
    private val viewModel by lazy {
        ViewModelProvider(this).get(RegisterActivityViewModel::class.java)
    }
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //view model
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        //firebase
        auth = Firebase.auth
        //click button register
        viewModel.ClickRegister = {
            val email:String = viewModel.email.value.toString()
            val password = viewModel.password.value.toString()
            Log.d(TAG, "onCreate: $email $password")
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Log.d(TAG, "onCreate: success")
                    val user = auth.currentUser
                    updateUI(user)
                }else{
                    Log.d(TAG, "onCreate: fail")
                    updateUI(null)
                }
            }.addOnCanceledListener {
                    Log.d(TAG, "onCreate: error")
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user != null){
            startIntent(this,SetUserActivity.Instance())
        }
    }


    companion object{
        private const val TAG = "RegisterActivity"
        fun Instance() = RegisterActivity()
    }
}