package com.example.trackies.homeScreen.data

import android.util.Log
import com.example.trackies.DateTimeClass
import com.example.trackies.homeScreen.buisness.LicenseViewState
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.homeScreen.buisness.entities.LicenseViewStateEntity
import com.example.trackies.homeScreen.buisness.entities.TrackieViewStateEntity
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HomeScreenRepository( val uniqueIdentifier: String ): Reads, Writes, DeleteTrackie {

    private val firebase: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val currentDateAndTime = DateTimeClass()

    private val users = firebase.collection("users")
    private val user = users.document(uniqueIdentifier)

    private val usersInformation = user.collection("user's information").document("license")

    private val usersTrackies = user.collection("user's trackies").document("trackies")
    private val namesOfTrackies = user.collection("names of trackies").document("names of trackies")

        private val usersWeeklyStatistics = user.collection("user's statistics").document("user's weekly statistics")

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

        users
            .document(uniqueIdentifier)
            .set({})
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
        usersTrackies
            .set({})
            .continueWith { usersTrackies.update(hashMapOf<String, Any>("arity" to FieldValue.delete())) }

//      "user's statistics" -> "user's weekly statistics"
        usersWeeklyStatistics
            .set({})
            .continueWith {
                usersWeeklyStatistics.update(hashMapOf<String, Any>("arity" to FieldValue.delete()))
            }
    }
    override suspend fun fetchUsersLicenseInformation(): LicenseViewState? {

        return suspendCoroutine {continuation ->

            usersInformation
                .get()
                .addOnSuccessListener { document ->

                    val licenseViewState = document.toObject(LicenseViewStateEntity::class.java)
                    if (licenseViewState != null) {
                        LicenseViewStateEntity(
                            active = licenseViewState.active,
                            validUntil = licenseViewState.validUntil,
                            totalAmountOfTrackies = licenseViewState.totalAmountOfTrackies
                        ).let { licenseEntity ->

                            if (licenseEntity.active != null && licenseEntity.totalAmountOfTrackies != null) {

                                val licenseInformation = LicenseViewState(
                                    active = licenseEntity.active,
                                    validUntil = licenseEntity.validUntil,
                                    totalAmountOfTrackies = licenseEntity.totalAmountOfTrackies
                                )

                                continuation.resume(licenseInformation)
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

                                val trackieAsNull = document.toObject(TrackieViewStateEntity::class.java)
                                if (trackieAsNull != null) {
                                    TrackieViewStateEntity(
                                        name = trackieAsNull.name,
                                        totalDose = trackieAsNull.totalDose,
                                        measuringUnit = trackieAsNull.measuringUnit,
                                        repeatOn = trackieAsNull.repeatOn,
                                        ingestionTime = trackieAsNull.ingestionTime
                                    ).let {trackieEntity ->

                                        // TODO: check whether all of the non null parameters are actually non null )

                                        if (trackieEntity.name != null && trackieEntity.totalDose != null && trackieEntity.measuringUnit != null && trackieEntity.repeatOn != null) {

                                            trackiesForToday.add(

                                                TrackieViewState(

                                                    name = trackieEntity.name,
                                                    totalDose = trackieEntity.totalDose,
                                                    measuringUnit = trackieEntity.measuringUnit,
                                                    repeatOn = trackieEntity.repeatOn,
                                                    ingestionTime = trackieEntity.ingestionTime
                                                )
                                            )
                                        }

                                        else { continuation.resume(null) }

                                    }
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.d("HomeScreenRepository", "fetchTrackiesForToday, an error occurred while fetching data, $exception")
                            }
                    }

                    Tasks.whenAllComplete(tasks).addOnCompleteListener { continuation.resume(trackiesForToday) }
                }

                else { continuation.resume(listOf()).also { Log.d("immagrinder", "2") } }
            }

            else { continuation.resume(null) }
        }
    }
    override suspend fun fetchNamesOfAllTrackies(): List<String>? = run { fetchNamesOfTrackies( array = "whole week") }
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
    override suspend fun addNewTrackie(trackieViewState: TrackieViewState): Boolean {

        val currentDayOfWeek = currentDateAndTime.getCurrentDayOfWeek()
        var passedCurrentDayOfWeek = false

        val licenseViewState = fetchUsersLicenseInformation()

        return suspendCoroutine { continuation ->

            if (licenseViewState != null) {

                licenseViewState.totalAmountOfTrackies

//              add new trackie to { (user's trackies) -> (trackies) }
                usersTrackies.collection(trackieViewState.name).document(trackieViewState.name).set(trackieViewState)

//              update total amount of trackies owned by the user { (user's information) -> (license) -> (totalAmountOfTrackies) }
                val updatedTotalAmountOfTrackies = (licenseViewState.totalAmountOfTrackies + 1)
                usersInformation.update("totalAmountOfTrackies", updatedTotalAmountOfTrackies)

//              add name of the trackie to { (names of trackies) -> (names of trackies) -> (whole week) }
                namesOfTrackies.update("whole week", FieldValue.arrayUnion(trackieViewState.name))

//              add name of the trackie to { (names of trackies) -> (names of trackies) -> (*particular day of week*) }
                trackieViewState.repeatOn.forEach { dayOfWeek ->

                    namesOfTrackies.update(dayOfWeek, FieldValue.arrayUnion(trackieViewState.name))
                }

//              add name of the trackies to { (user's statistics) -> user's weekly statistics) -> (*particular day of week*)
                listOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday").forEach { dayOfWeek ->

                    if (!passedCurrentDayOfWeek) {

                        passedCurrentDayOfWeek =
                            if (currentDayOfWeek == dayOfWeek) { true }
                            else { false }
                    }

                    if (trackieViewState.ingestionTime == null) {

                        if (trackieViewState.repeatOn.contains(dayOfWeek)) {

                            val fieldToSave = mutableMapOf<String, Boolean>()

                            fieldToSave["ingested"] =
                                if (!passedCurrentDayOfWeek) { true }
                                else if (currentDayOfWeek == dayOfWeek) { false }
                                else {false}

                            usersWeeklyStatistics
                                .collection(dayOfWeek)
                                .document(trackieViewState.name)
                                .set(fieldToSave)
                                .addOnSuccessListener { Log.d("HomeScreenRepository, addNewTrackie, ingestionTime == null", "savedSuccessfully") }
                                .addOnSuccessListener { Log.d("HomeScreenRepository, addNewTrackie, ingestionTime == null", "an error occurred, $it") }
                        }
                    }
                }

                continuation.resume(true)
            }

            else { continuation.resume(false) }

        }
    }
    override suspend fun fetchAllTrackies(): List<TrackieViewState>? {

        val namesOfTrackiesForToday: List<String>? = fetchNamesOfTrackies("whole week")
        val trackiesForToday: MutableList<TrackieViewState> = mutableListOf()

        return suspendCoroutine { continuation ->

            if (namesOfTrackiesForToday != null) {

                if (namesOfTrackiesForToday.isNotEmpty()) {

                    val tasks = namesOfTrackiesForToday.map { nameOfTheTrackie ->

                        usersTrackies.collection(nameOfTheTrackie).document(nameOfTheTrackie)
                            .get()
                            .addOnSuccessListener { document ->

                                val trackieAsNull = document.toObject(TrackieViewStateEntity::class.java)
                                if (trackieAsNull != null) {
                                    TrackieViewStateEntity(
                                        name = trackieAsNull.name,
                                        totalDose = trackieAsNull.totalDose,
                                        measuringUnit = trackieAsNull.measuringUnit,
                                        repeatOn = trackieAsNull.repeatOn,
                                        ingestionTime = trackieAsNull.ingestionTime
                                    ).let {trackieEntity ->

                                        if (trackieEntity.name != null && trackieEntity.totalDose != null && trackieEntity.measuringUnit != null && trackieEntity.repeatOn != null) {

                                            trackiesForToday.add(

                                                TrackieViewState(

                                                    name = trackieEntity.name,
                                                    totalDose = trackieEntity.totalDose,
                                                    measuringUnit = trackieEntity.measuringUnit,
                                                    repeatOn = trackieEntity.repeatOn,
                                                    ingestionTime = trackieEntity.ingestionTime
                                                )
                                            )
                                        }

                                        else { continuation.resume(null) }

                                    }
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.d("HomeScreenRepository", "fetchAllTrackies, an error occurred while fetching data, $exception")
                            }
                    }

                    Tasks.whenAllComplete(tasks).addOnCompleteListener { continuation.resume(trackiesForToday) }
                }

                else { continuation.resume(listOf<TrackieViewState>()) }
            }

            else { continuation.resume(null) }
        }
    }
    override fun checkTrackieAsIngestedForToday(trackieViewState: TrackieViewState) {

        Log.d("checkAsIngestedForToday", trackieViewState.name)

        if (trackieViewState.ingestionTime == null) {

            trackieViewState.repeatOn.forEach { dayOfWeek ->

                usersWeeklyStatistics
                    .collection(currentDateAndTime.getCurrentDayOfWeek()) // current day of week
                    .document(trackieViewState.name)
                    .update("ingested", true)
                    .addOnSuccessListener { Log.d("HomeScreenRepository, checkTrackieAsIngestedForToday, ingestionTime == null", "updatedSuccessfully") }
                    .addOnFailureListener { Log.d("HomeScreenRepository, checkTrackieAsIngestedForToday, ingestionTime == null", "an error occurred, $it") }
            }
        }
    }

    override suspend fun fetchStatesOfTrackiesForToday(): Map<String, Boolean>? {

        val namesOfTrackiesForToday: List<String>? = fetchNamesOfTrackies(currentDateAndTime.getCurrentDayOfWeek())
        var namesAndStatesOfTrackies = mutableMapOf<String, Boolean>()

        return suspendCoroutine { continuation ->

            if (namesOfTrackiesForToday != null) {

                if (namesOfTrackiesForToday.isNotEmpty()) {

                    namesOfTrackiesForToday.onEach {nameOfTheTrackie ->

                        usersWeeklyStatistics
                            .collection(currentDateAndTime.getCurrentDayOfWeek())
                            .document(nameOfTheTrackie)
                            .get()
                            .addOnSuccessListener {document ->

                                document.getBoolean("ingested").let { stateOfTheTrackie ->

                                    if (stateOfTheTrackie != null) {

                                        namesAndStatesOfTrackies[nameOfTheTrackie] = stateOfTheTrackie

                                        if (namesAndStatesOfTrackies.size == namesOfTrackiesForToday.size) {

                                            continuation.resume(namesAndStatesOfTrackies)
                                        }
                                    }

                                    else { continuation.resume(null) }
                                }
                            }
                            .addOnFailureListener {

                                Log.d("HomeScreenRepository, fetchStatesOfTrackiesForToday", "$it")
                            }
                    }
                }

                else { continuation.resume( emptyMap()) }
            }

            else { continuation.resume(null) }
        }
    }

    // TODO: this function fucks up
    override suspend fun fetchWeeklyRegularity(): Map<String, Map<Int, Int>>? {

        return suspendCoroutine { continuation ->

            val mapWithCalculatedRegularity = mutableMapOf<String, Map<Int, Int>>()
            val currentDayOfWeek = currentDateAndTime.getCurrentDayOfWeek()
            var foundCurrentDayOfWeek = false

            val jobs = listOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday").map { dayOfWeek ->

                CoroutineScope(Dispatchers.Default).async {

                    if (!foundCurrentDayOfWeek) {
                        
                        val namesOfTrackiesForThisDayOfWeek = fetchNamesOfTrackies(dayOfWeek)

                        var totalAmount = 0
                        var ingestedAmount = 0

                        if (namesOfTrackiesForThisDayOfWeek != null) {

                            if (namesOfTrackiesForThisDayOfWeek.isNotEmpty()) {

                                for (nameOfTrackie in namesOfTrackiesForThisDayOfWeek) {
                                    val documentSnapshot = usersWeeklyStatistics
                                        .collection(dayOfWeek)
                                        .document(nameOfTrackie)
                                        .get()
                                        .await()

                                    documentSnapshot.getBoolean("ingested")?.let { ingested ->
                                        totalAmount += 1
                                        if (ingested) {
                                            ingestedAmount += 1
                                        }
                                    }
                                }

                                mapWithCalculatedRegularity[dayOfWeek] = mapOf(totalAmount to ingestedAmount)
                            }

                            else { mapWithCalculatedRegularity[dayOfWeek] = mapOf(0 to 0) }

                            if (dayOfWeek == currentDayOfWeek) { foundCurrentDayOfWeek = true }
                        }

                        else { continuation.resume(null) }
                    }

                    else { mapWithCalculatedRegularity[dayOfWeek] = mapOf(0 to 0) }
                }
            }

            CoroutineScope(Dispatchers.Default).launch {
                jobs.awaitAll()

                val mapToReturn = mapOf(
                    "monday" to mapWithCalculatedRegularity["monday"]!!,
                    "tuesday" to mapWithCalculatedRegularity["tuesday"]!!,
                    "wednesday" to mapWithCalculatedRegularity["wednesday"]!!,
                    "thursday" to mapWithCalculatedRegularity["thursday"]!!,
                    "friday" to mapWithCalculatedRegularity["friday"]!!,
                    "saturday" to mapWithCalculatedRegularity["saturday"]!!,
                    "sunday" to mapWithCalculatedRegularity["sunday"]!!,
                )

                continuation.resume(mapToReturn)
            }
        }
    }

    override suspend fun decreaseAmountOfTrackies(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        usersInformation
            .get()
            .addOnSuccessListener { document ->

                val fetchedCurrentTotalAmountOfTrackies = document.get("totalAmountOfTrackies")

                if (fetchedCurrentTotalAmountOfTrackies != null) {

                    val updatedTotalAmountOfTrackies = fetchedCurrentTotalAmountOfTrackies.toString().toInt() - 1

                    usersInformation.update("totalAmountOfTrackies", updatedTotalAmountOfTrackies)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { onFailure("$it") }
                }

            }
            .addOnFailureListener { onFailure(it.toString()) }
    }

    override suspend fun deleteTrackieFromNamesOfTrackies(
        trackieViewState: TrackieViewState,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        namesOfTrackies
            .get()
            .addOnSuccessListener { document ->

                listOf("whole week", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday").forEach { array ->

                    val listOfAllNames = document.get(array) as MutableList<String>?

                    if (!listOfAllNames.isNullOrEmpty()) {

                        if (listOfAllNames.contains(trackieViewState.name)) {

                            listOfAllNames.remove(trackieViewState.name)
                            namesOfTrackies.update(array, listOfAllNames)
                        }
                    }

                    else { onFailure("there's not such element like ${trackieViewState.name}") }
                }
            }
    }

    override suspend fun deleteTrackieFromUsersWeeklyStatistics(
        trackieViewState: TrackieViewState,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        trackieViewState.repeatOn.forEach { dayOfWeek ->

            usersWeeklyStatistics
                .collection(dayOfWeek)
                .document(trackieViewState.name)
                .delete()
        }
    }

    override suspend fun deleteTrackieFromUsersTrackies(
        trackieViewState: TrackieViewState,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        fetchAllTrackies()
        usersTrackies
            .collection(trackieViewState.name)
            .document(trackieViewState.name)
            .delete()

    }

    override suspend fun fetchWeeklyRegularityOfTheTrackie(trackieViewState: TrackieViewState): MutableMap<String, Map<String, Int>>? {

        val mapToReturn = mutableMapOf<String, Map<String, Int>>() // name of the trackie to subsequentMap
        var subsequentMap = mutableMapOf<String, Int>() // day of week to percentage

        return try {

            for (dayOfWeek in trackieViewState.repeatOn) {

                val document = usersWeeklyStatistics
                    .collection(dayOfWeek)
                    .document(trackieViewState.name)
                    .get()
                    .await()

                val ingested = document.getBoolean("ingested")

                if (ingested != null) {

                    when (ingested) {

                        true -> subsequentMap[dayOfWeek] = 100

                        false -> subsequentMap[dayOfWeek] = 0
                    }
                }
                else { return null }
            }

            mapToReturn[trackieViewState.name] = subsequentMap

            mapToReturn

        }
        catch (e: Exception) { null }
    }
}