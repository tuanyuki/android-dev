package com.example.chatkotlinv2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.chatkotlinv2.databinding.ActivityIntroBinding
import com.example.chatkotlinv2.viewmodel.IntroActivityViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class IntroActivity : AppCompatActivity() {
    private lateinit var binding:ActivityIntroBinding
    private val TAG = "tuananh"
    private lateinit var auth: FirebaseAuth

    private val viewModel by lazy {
        ViewModelProvider(this).get(IntroActivityViewModel::class.java)
    }
    //dang ky Activity for result
    private val googleIntent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            val data = result.data
            doTask(data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //khoi tao firebase
        auth = Firebase.auth
        //khoi tao viewmodel
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        //chuyen activity
        viewModel.clickSignUp = {
            startIntent(T = this,V = RegisterActivity.Instance())
        }
        viewModel.clickSignIn = {
            startIntent(T = this,V = LoginActivity.Instance())
        }

        viewModel.clickGoogle = {
            Log.d("TAG", "onClick: true")
            signIn(configureGoogle())
        }
    }


    //configure google sign in
    private fun configureGoogle(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(this, gso)
    }

    private fun signIn(googleSignInClient:GoogleSignInClient) {
        val signInIntent = googleSignInClient.signInIntent
        googleIntent.launch(signInIntent)
    }
    private fun doTask(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = task.getResult(ApiException::class.java)!!
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Log.w(TAG, "Google sign in failed", e)
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null)
            startIntent(this,SetUserActivity.Instance())
    }
}