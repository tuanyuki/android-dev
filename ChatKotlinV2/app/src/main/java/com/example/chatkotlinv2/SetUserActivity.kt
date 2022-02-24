package com.example.chatkotlinv2

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import android.widget.PopupMenu.OnMenuItemClickListener
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.chatkotlinv2.data.User
import com.example.chatkotlinv2.databinding.ActivitySetUserBinding
import com.example.chatkotlinv2.viewmodel.SetUserActivityViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SetUserActivity : AppCompatActivity(), OnMenuItemClickListener {
    private lateinit var binding:ActivitySetUserBinding
    private lateinit var user: FirebaseUser
    private var photo: Uri = Uri.parse("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR8hQCe_BFN9p7BvUCZC4UQYFMyqvWXme86WA&usqp=CAU")
    private lateinit var database: DatabaseReference
    private val viewModel by lazy {
        ViewModelProvider(this).get(SetUserActivityViewModel::class.java)
    }
    private val cameraResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data: Intent? = result.data
            photo = data?.extras?.get("data") as Uri
            binding.IMG.setImageURI(photo)
        }
    }
    private val imageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data: Intent? = result.data
            photo = data?.data!!
            binding.IMG.setImageURI(photo)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //init viewmodel
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        //init firebase auth
        user = Firebase.auth.currentUser!!
        //init database
        database = Firebase.database.reference

        viewModel.ClickIMG = {
            PopupMenu(this, binding.IMG).apply {
                // MainActivity implements OnMenuItemClickListener
                setOnMenuItemClickListener(this@SetUserActivity)
                inflate(R.menu.popup_img)
                show()
            }
        }
        viewModel.ClickConfirm = {
            val phofileUpdates = userProfileChangeRequest {
                displayName = viewModel.username.toString()
                photoUri = photo
            }
            user.updateProfile(phofileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Log.d(TAG, "onCreate: update Profile success")
                        startIntent(this,MainActivity.Instance())
                    }
                }
            val user = User(viewModel.username.value.toString(),this.user.uid)
            Log.d(TAG, "onCreate: ${viewModel.username.value} , ${this.user.uid}")
            database.child("users").child("${this.user.uid}}").setValue(user)
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.Camera -> {
                checkPermissionCamera()
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraResult.launch(intent)
                true
            }R.id.Local -> {
                openGalleryForImage()
                true
            }else -> false
        }
    }

    private fun checkPermissionLocalImage() {
        if(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),123)
        }
    }

    private fun checkPermissionCamera() {
        if(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED)
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),123)
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        imageResult.launch(intent)

    }

    override fun onStart() {
        checkPermissionLocalImage()
        super.onStart()
    }

    companion object{
        private const val TAG = "LoginActivity"
        fun Instance() = SetUserActivity()
    }
}
