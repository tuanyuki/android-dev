package com.example.chatkotlinv2.viewmodel

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class RegisterActivityViewModel:ViewModel() {
    var email:MediatorLiveData<String> = MediatorLiveData()
    var password:MediatorLiveData<String> = MediatorLiveData()
    private var _canRegister:MediatorLiveData<Boolean> = MediatorLiveData()
    val canRegister:MediatorLiveData<Boolean> get() = _canRegister
    var ClickRegister:()->Unit = {}
    //init
    init {
        _canRegister.addSource(email){
            _canRegister.value = checkCanRegister()
        }
        _canRegister.addSource(password){
            _canRegister.value = checkCanRegister()
        }
    }
    //check email
    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    private fun checkCanRegister(): Boolean {
        Log.d("123", "checkCanRegister: ${checkEmail()} ${checkPassword(password.value?.length ?: 0)} ${email.value} ${password.value}")
        if (checkEmail() && checkPassword(password.value?.length ?: 0)){
            return true
        }
        else{
            return false
        }
    }

    private fun checkPassword(length:Int):Boolean{
        if (length >= 6) return true
        else return false
    }

    private fun checkEmail(): Boolean {
        if (email.value == null) return false
        return EMAIL_ADDRESS_PATTERN.matcher(email.value!!).matches()
    }

    fun onClickRegister(){
        ClickRegister()
    }
}