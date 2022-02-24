package com.example.chatkotlinv2.viewmodel

import androidx.lifecycle.ViewModel

class IntroActivityViewModel: ViewModel() {
    var clickSignUp:()->Unit = {}
    var clickSignIn:()->Unit = {}
    var clickGoogle:()->Unit = {}
    fun onClickSignUp(){
        clickSignUp()
    }
    fun onClickSignIn(){
        clickSignIn()
    }
    fun onClickGoogle(){
        clickGoogle()
    }
}