package com.example.trackies.homeScreen.data

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import com.example.trackies.DateTimeClass
import com.example.trackies.homeScreen.buisness.LicenseViewState
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
class HomeScreenRepository( val uniqueIdentifier: String ): Reads, Writes {

    private val firebase: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val currentDateAndTime = DateTimeClass()

    private val users = firebase.collection("users")
    private val user = users.document(uniqueIdentifier)

    private val usersInformation = user.collection("user's information").document("license")

    private val usersTrackies = user.collection("user's trackies").document("trackies")
    private val namesOfTrackies = user.collection("names of trackies").document("names of trackies")

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

    private fun addNewUser() {

        users.document(uniqueIdentifier).set({})
            .continueWith {
                val update = hashMapOf<String, Any>(
                    "arity" to FieldValue.delete()
                )
                user.update(update)
            }

//      "user's information" -> "license"
        usersInformation.set(LicenseViewState(active = false, validUntil = null, totalAmountOfTrackies = 0))

//      "names of trackies" -> "names of trackies"
        namesOfTrackies.set( hashMapOf("MONDAY" to listOf<String>()) )
        namesOfTrackies.update("TUESDAY", listOf<String>())
        namesOfTrackies.update("WEDNESDAY", listOf<String>())
        namesOfTrackies.update("THURSDAY", listOf<String>())
        namesOfTrackies.update("FRIDAY", listOf<String>())
        namesOfTrackies.update("SATURDAY", listOf<String>())
        namesOfTrackies.update("SUNDAY", listOf<String>())

//      "user's trackies" -> "trackies"
        usersTrackies.set({})
            .continueWith { usersTrackies.update(hashMapOf<String, Any>("arity" to FieldValue.delete())) }

//      "user's statistics" -> "statistics"
        usersStatistics.set({})
            .continueWith { usersStatistics.update(hashMapOf<String, Any>("arity" to FieldValue.delete())) }
    }

    override suspend fun fetchUsersLicenseInformation(): LicenseViewState? {

        return suspendCoroutine {continuation ->

            usersInformation
                .get()
                .addOnSuccessListener { document ->

                    val active = document.getBoolean("active")
                    val validUntil = document.getString("validUntil")
                    val totalAmountOfTrackies = document.getString("totalAmountOfTrackies")

                    if ( active != null && totalAmountOfTrackies != null ) { continuation.resume(LicenseViewState( active = active, validUntil = validUntil, totalAmountOfTrackies = totalAmountOfTrackies.toInt())) }
                    else { continuation.resume(null) }
                }
                .addOnFailureListener { continuation.resume(null) }
        }
    }

    override suspend fun fetchTrackiesForToday(): List<TrackieViewState> {

        val namesOfTrackiesForToday: List<String> = fetchNamesOfTrackiesForToday()
        var trackiesForToday: MutableList<TrackieViewState> = mutableListOf()

        return suspendCoroutine { continuation ->

            if (namesOfTrackiesForToday.isNotEmpty()) {

                namesOfTrackiesForToday.forEach { nameOfTheTrackie ->

                    usersTrackies
                        .get()
                        .addOnSuccessListener {

                            val trackie = it.get(nameOfTheTrackie) as? TrackieViewState

                            if (trackie != null) {
                                trackiesForToday.add(trackie)
                            }

                            else { return@addOnSuccessListener }
                        }
                        .addOnFailureListener {
                            Log.d("halla!", "an error occurred while fetching trackies for today, $it")
                        }
                }

                continuation.resume(trackiesForToday.toList())
            }

            else {
                Log.d("halla!", "trackiesForToday is null")
            }
        }
    }

    private suspend fun fetchNamesOfTrackiesForToday(): List<String> {

        val currentDayOfWeek = currentDateAndTime.getCurrentDayOfWeek()

        return suspendCoroutine { continuation ->

            namesOfTrackies
                .get()
                .addOnSuccessListener {

                    Log.d("halla!", "im in")

                    val listOfTrackiesForToday = it.get(currentDayOfWeek) as? List<String>

                    if (listOfTrackiesForToday != null) {
                        continuation.resume(listOfTrackiesForToday)
                        Log.d("halla!", "that's the list: $listOfTrackiesForToday")
                    }

                    else {
                        Log.d("halla!", "null")
                        return@addOnSuccessListener
                    }
                }
                .addOnFailureListener { Log.d("halla!", "$it") }
        }
    }

}