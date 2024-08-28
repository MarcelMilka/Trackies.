package com.example.trackies.detailedTrackie.data

import com.example.trackies.detailedTrackie.buisness.TrackieWithWeeklyRegularity
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DetailedTrackieRepository( val uniqueIdentifier: String ): Reads {

    private val firebase: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val users = firebase.collection("users")
    private val user = users.document(uniqueIdentifier)
    private val usersWeeklyStatistics = user.collection("user's statistics").document("user's weekly statistics")

    override suspend fun fetchWeeklyRegularityOfTheTrackie(trackieViewState: TrackieViewState): TrackieWithWeeklyRegularity? {

        val regularity = mutableMapOf<String, Int>()

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

                        true -> regularity[dayOfWeek] = 100

                        false -> regularity[dayOfWeek] = 0
                    }
                }
                else { return null }
            }

            TrackieWithWeeklyRegularity(name = trackieViewState.name, regularity)
        }
        catch (e: Exception) { null }
    }
}