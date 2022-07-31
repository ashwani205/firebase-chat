package com.example.firebaseproject.chat.model

class Message {
    var message: String? = null
    var senderUId: String? = null

    constructor() {}

    constructor(message: String?, senderUId: String?) {
        this.message = message
        this.senderUId = senderUId
    }
}