package com.example.trackies.detailedTrackie.data

import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DetailedTrackieRepository( val uniqueIdentifier: String ): Reads {

    private val firebase: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val users = firebase.collection("users")
    private val user = users.document(uniqueIdentifier)
    private val usersWeeklyStatistics = user.collection("user's statistics").document("user's weekly statistics")

    override suspend fun fetchWeeklyRegularityOfTheTrackie(trackieViewState: TrackieViewState): MutableMap<String, Map<String, Int>>? {

        val mapToReturn = mutableMapOf<String, Map<String, Int>>()
        val valueOfMapToReturn = mutableMapOf<String, Int>()

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

                        true -> valueOfMapToReturn[dayOfWeek] = 100

                        false -> valueOfMapToReturn[dayOfWeek] = 0
                    }
                }
                else { return null }
            }

            mapToReturn[trackieViewState.name] = valueOfMapToReturn
            mapToReturn

        }
        catch (e: Exception) { null }
    }
}