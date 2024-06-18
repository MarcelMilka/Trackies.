package com.example.trackies.homeScreen.data

import android.util.Log
import com.example.trackies.homeScreen.viewState.License
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore


class HomeScreenRepository( val uniqueIdentifier: String ): HomeScreenRepositoryInterface {

    private val firebase: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val users = firebase.collection("users")
    private val user = users.document(uniqueIdentifier)

    private val usersInformation = user.collection("user's information").document("license")
    private val usersTrackies = user.collection("user's trackies").document("trackies")
    private val usersStatistics = user.collection("user's statistics").document("statistics")
    override fun isFirstTimeInApp() {

        users.document(uniqueIdentifier)
            .get()
            .addOnSuccessListener { user ->

                if (user.exists()) {
                    Log.d("halla!", "i should fetch the user's data")
                }

                else {
                    Log.d("halla!", "i should create a new user")
                    addNewUser()
                }
            }
            .addOnFailureListener { Log.d("halla!", "an error occurred while loading data") }
    }

    override fun addNewUser() {

//      add the user to the database (the document which contains user's data is named after the user's unique identifier)
        users.document(uniqueIdentifier).set({})
            .continueWith {
                val update = hashMapOf<String, Any>(
                    "arity" to FieldValue.delete()
                )
                user.update(update)
            }

//      add a document which is responsible for "telling" whether the user has premium app
        usersInformation.set(License(isActive = false, isValidUntil = null))

//      add a document which is responsible for storing the user's trackies
        usersTrackies.set({})
            .continueWith { usersTrackies.update(hashMapOf<String, Any>("arity" to FieldValue.delete())) }

//      add a document which is responsible for storing the user's statistics (weekly, in the future monthly and annual)
        usersStatistics.set({})
            .continueWith { usersStatistics.update(hashMapOf<String, Any>("arity" to FieldValue.delete())) }
    }
}