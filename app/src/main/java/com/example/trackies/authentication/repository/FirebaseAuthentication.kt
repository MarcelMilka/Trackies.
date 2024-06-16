package com.example.trackies.authentication.repository

import android.util.Patterns
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth

class FirebaseAuthentication {

    private val authentication = Firebase.auth

    fun getSignedInUser(): String? = authentication.currentUser?.run { this.uid }

    fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        signUpError: (String) -> Unit,
        authenticationResult: (Boolean) -> Unit
    ) {

        if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signUpError("Invalid email format")
        }

        authentication.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { process ->

                if (process.isSuccessful) {

                    sendEmailToVerifySigningUp { gotSent ->

                        if (gotSent) {
                            authenticationResult(true)
                        }

                        else {
                            authenticationResult(false)
                        }
                    }
                }

                else {

                    when (val exception = process.exception) {

                        is FirebaseAuthInvalidCredentialsException -> {
                            signUpError("${exception.message}")
                        }

                        is FirebaseAuthUserCollisionException -> {
                            signUpError("$email is already used by another account.")
                        }

                        else -> {
                            signUpError("${exception?.message}")
                        }
                    }
                }
            }
    }

    private fun sendEmailToVerifySigningUp( verificationEmailGotSent: (Boolean) -> Unit ) {

        authentication.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener {process ->

                if (process.isSuccessful) { verificationEmailGotSent(true) }

                else { verificationEmailGotSent(false) }
            }
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        signInError: (String) -> Unit,
        authenticatedSuccessfully: (String) -> Unit
    ) {

        authentication.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { process ->

                if (process.isSuccessful) {

                    val user = authentication.currentUser

                    user?.isEmailVerified?.let { isVerified ->

                        if (isVerified) {
                            authenticatedSuccessfully(user.uid)
                        }
                    }
                }
                else { signInError("${process.exception}") }
            }
    }

    fun signOut() { authentication.signOut() }

    fun deleteAccount(
        onComplete: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        authentication.currentUser?.delete()
            ?.addOnCompleteListener {process ->

                if (process.isSuccessful) {
                    onComplete()
                }
            }
            ?.addOnFailureListener { exception ->
                onFailure("$exception")
            }
    }

    fun recoverThePassword(
        email: String,
        successfullySentEmail: () -> Unit,
        failedToSendEmail: (String) -> Unit
    ) {
        authentication.sendPasswordResetEmail( email )
            .addOnCompleteListener {process ->

                if (process.isSuccessful) {
                    successfullySentEmail()
                }

                else {
                    failedToSendEmail("${process.exception}")
                }
            }
    }
}