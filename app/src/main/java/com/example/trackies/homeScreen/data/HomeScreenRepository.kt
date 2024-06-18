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
    private val usersTrackies = user.collection("user's data").document("trackies")
    private val usersStatistics = user.collection("user's data").document("statistics")


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

//      add the user
        users.document(uniqueIdentifier).set({})
            .continueWith {
                val update = hashMapOf<String, Any>(
                    "arity" to FieldValue.delete()
                )
                user.update(update)
            }

//      add a collection which is going to represent user's information (whether the license is valid or no etc.)
        usersInformation.set(License(isActive = false, isValidUntil = null))

//      add a collection which is going to represent user's trackies
        usersTrackies.set({})
            .continueWith {
                val update = hashMapOf<String, Any>(
                    "arity" to FieldValue.delete()
                )
                usersTrackies.update(update)
            }

//      add a collection which is going to represent user's statistics
        usersStatistics.set({})
            .continueWith {
                val update = hashMapOf<String, Any>(
                    "arity" to FieldValue.delete()
                )
                usersStatistics.update(update)
            }

    }
}