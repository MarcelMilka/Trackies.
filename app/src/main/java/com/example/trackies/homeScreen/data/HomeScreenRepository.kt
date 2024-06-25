package com.example.trackies.homeScreen.data

import android.util.Log
import com.example.trackies.DateTimeClass
import com.example.trackies.homeScreen.buisness.LicenseViewState
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.google.android.gms.tasks.Tasks
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

                if (!(user.exists())) {
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
        namesOfTrackies.set( hashMapOf("whole week" to listOf<String>()) )
        namesOfTrackies.update("monday", listOf<String>() )
        namesOfTrackies.update("tuesday", listOf<String>())
        namesOfTrackies.update("wednesday", listOf<String>())
        namesOfTrackies.update("thursday", listOf<String>())
        namesOfTrackies.update("friday", listOf<String>())
        namesOfTrackies.update("saturday", listOf<String>())
        namesOfTrackies.update("sunday", listOf<String>())

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

                    val licenseViewState = document.toObject(LicenseViewState::class.java)
                    if (licenseViewState != null) {
                        LicenseViewState(
                            active = licenseViewState.active,
                            validUntil = licenseViewState.validUntil,
                            totalAmountOfTrackies = licenseViewState.totalAmountOfTrackies
                        ).let {fetchedLicenseInformation ->

                            if (fetchedLicenseInformation.active != null && fetchedLicenseInformation.totalAmountOfTrackies != null) {
                                continuation.resume(fetchedLicenseInformation)
                            }

                            else {
                                continuation.resume(null)
                            }
                        }
                    }

                    else { return@addOnSuccessListener }
                }
                .addOnFailureListener { continuation.resume(null) }
        }
    }

    override suspend fun fetchTrackiesForToday(): List<TrackieViewState>? {

        val namesOfTrackiesForToday: List<String>? = fetchNamesOfTrackies(currentDateAndTime.getCurrentDayOfWeek())
        val trackiesForToday: MutableList<TrackieViewState> = mutableListOf()

        return suspendCoroutine { continuation ->

            if (namesOfTrackiesForToday != null) {

                if (namesOfTrackiesForToday.isNotEmpty()) {

                    val tasks = namesOfTrackiesForToday.map { nameOfTheTrackie ->

                        usersTrackies.collection(nameOfTheTrackie).document(nameOfTheTrackie)
                            .get()
                            .addOnSuccessListener { document ->

                                val trackieAsNull = document.toObject(TrackieViewState::class.java)
                                if (trackieAsNull != null) {
                                    TrackieViewState(
                                        name = trackieAsNull.name,
                                        totalDose = trackieAsNull.totalDose,
                                        measuringUnit = trackieAsNull.measuringUnit,
                                        repeatOn = trackieAsNull.repeatOn,
                                        ingestionTime = trackieAsNull.ingestionTime
                                    ).let {
                                        trackiesForToday.add(it)
                                    }
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.d("HomeScreenRepository", "fetchTrackiesForToday, an error occurred while fetching data, $exception")
                            }
                    }

                    Tasks.whenAllComplete(tasks).addOnCompleteListener { continuation.resume(trackiesForToday) }
                }

                else { continuation.resume(listOf<TrackieViewState>()) }
            }

            else { continuation.resume(null) }
        }
    }

    override suspend fun fetchNamesOfAllTrackies(): List<String>? = run { fetchNamesOfTrackies( array = "whole week" ) }

    private suspend fun fetchNamesOfTrackies(array: String): List<String>? {

        return suspendCoroutine { continuation ->

            namesOfTrackies
                .get()
                .addOnSuccessListener {document ->

                    if (document.exists()) {

                        val listOfTrackiesForToday = document.get(array) as? List<String>

                        if (listOfTrackiesForToday != null) {

                            continuation.resume(listOfTrackiesForToday)
                            Log.d("HomeScreenRepository", "fetchNamesOfTrackiesForToday, that's the list of names of trackies for today: $listOfTrackiesForToday")
                        }

                        else {

                            Log.d("HomeScreenRepository", "fetchNamesOfTrackiesForToday, listOfTrackiesForToday is equal to null")
                            return@addOnSuccessListener
                        }
                    }

                    else {
                        Log.d("HomeScreenRepository", "fetchNamesOfTrackiesForToday, the document does not exist")
                    }
                }

                .addOnFailureListener { Log.d("HomeScreenRepository", "fetchNamesOfTrackiesForToday, $it") }
        }
    }

    override suspend fun addNewTrackie(trackieViewState: TrackieViewState) {

//          add new trackie to { (user's trackies) -> (trackies) }

//          update total amount of trackies owned by the user { (user's information) -> (license) -> (totalAmountOfTrackies) }

//          add name of the trackie to { (names of trackies) -> (names of trackies) -> (whole week) }

//          add name of the trackie to { (names of trackies) -> (names of trackies) -> (*particular day of week*) }
    }
}