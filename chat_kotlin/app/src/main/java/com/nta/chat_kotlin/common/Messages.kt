package com.nta.chat_kotlin.common

class Messages {
    var messages:String? = null
    var uid:String?      = null


    constructor(messages: String?,uid:String?) {
        this.messages = messages
        this.uid      = uid
    }

    constructor()
}