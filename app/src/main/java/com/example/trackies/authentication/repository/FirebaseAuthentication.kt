package com.example.trackies.authentication.repository

import com.example.trackies.authentication.firebaseUser.FirebaseUser
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class FirebaseAuthentication {

    val authentication = Firebase.auth

    fun getSignedInUser(): FirebaseUser? = authentication.currentUser?.run {

        FirebaseUser( usersID = this.uid )
    }
}