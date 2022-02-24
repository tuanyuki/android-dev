package com.example.chatkotlinv2.viewmodel

import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

class SetUserActivityViewModel:ViewModel() {
    var username = MediatorLiveData<String>()
    var _confirm = MediatorLiveData<Boolean>()
    var ClickIMG:()->Unit = {}
    var ClickConfirm:()->Unit = {}
    val confirm get() = _confirm
    init {
        _confirm.addSource(username){
            _confirm.value = Confirm()
        }
    }

    fun Confirm():Boolean{
        if (username.value?.length ?: 0  >= 4){
            return true
        }
        return false
    }
    fun onClickIMG(){
        ClickIMG()
    }
    fun onClickConfirm(){
        ClickConfirm()
    }
}