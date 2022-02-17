package com.nta.chat_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nta.chat_kotlin.common.User

class SignUp_Activity : AppCompatActivity() {
    private lateinit var edtEmail:EditText
    private lateinit var edtUsername:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnRegister: Button
    private lateinit var mAuth:FirebaseAuth
    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        edtEmail    = findViewById(R.id.idEDTemail_Signup)
        edtUsername = findViewById(R.id.idEDTusername_Signup)
        edtPassword = findViewById(R.id.idEDTpassword_Signup)
        btnRegister = findViewById(R.id.idBTNregister_Signup)
        mAuth       = FirebaseAuth.getInstance()
        reference   = FirebaseDatabase.getInstance().getReference();
        btnRegister.setOnClickListener {
            if(edtEmail.text.toString().isEmpty() || edtUsername.text.toString().isEmpty() || edtPassword.text.toString().isEmpty()){
                Toast.makeText(this@SignUp_Activity,"please input enough data",Toast.LENGTH_SHORT).show()
            }else{
                Register(edtEmail.text.toString(),edtUsername.text.toString(),edtPassword.text.toString())
            }
        }
    }
    private fun Register(email: String, username: String, password:String){
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this@SignUp_Activity){
                if(it.isSuccessful){
                    var user:User = User(username,email,
                        mAuth.uid.toString())
                    reference.child("user").child(mAuth.uid.toString()).setValue(user)
                    finish()
                    startActivity(Intent(this@SignUp_Activity,HomeActivity::class.java))
                }else{
                    Toast.makeText(this@SignUp_Activity,"some error",Toast.LENGTH_SHORT).show()
                }
            }
    }
}
