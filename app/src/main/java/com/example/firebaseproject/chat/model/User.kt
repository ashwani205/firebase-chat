package com.example.firebaseproject.chat.model

class User {
    var name: String? = null
    var email: String? = null
    var uId: String? = null

    constructor() {}

    constructor(name: String?, email: String?, uid: String?) {
        this.name = name
        this.email = email
        this.uId = uid
    }
}