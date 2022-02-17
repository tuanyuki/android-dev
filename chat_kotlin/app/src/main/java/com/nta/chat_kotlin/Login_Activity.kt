package com.nta.chat_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login_Activity : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        edtEmail     = findViewById(R.id.idEDTusername_Main)
        edtPassword  = findViewById(R.id.idEDTpassword_Main)
        btnLogin     = findViewById(R.id.idBTNlogin_Main)
        mAuth        = FirebaseAuth.getInstance()
        btnLogin.setOnClickListener {
            //check input data
            if(edtEmail.text.toString().isEmpty()||edtPassword.text.toString().isEmpty()){
                Toast.makeText(this@Login_Activity,"please input enough data",Toast.LENGTH_SHORT).show()
            }
            else{
                //logic of loging
                Login(edtEmail.text.toString(),edtPassword.text.toString())
            }
        }
    }

    private fun Login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this@Login_Activity){
                if(it.isSuccessful){
                    val intent:Intent = Intent(this@Login_Activity,HomeActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@Login_Activity,"some error",Toast.LENGTH_SHORT).show()
                }
            }
    }
}