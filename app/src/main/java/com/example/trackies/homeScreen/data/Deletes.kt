package com.example.trackies.homeScreen.data

import com.example.trackies.homeScreen.buisness.TrackieViewState

interface DeleteTrackie {

    suspend fun decreaseAmountOfTrackies(onSuccess: () -> Unit, onFailure: (String) -> Unit)

    suspend fun deleteTrackieFromNamesOfTrackies()

    suspend fun deleteTrackieFromUsersWeeklyStatistics()

    suspend fun deleteTrackieFromUsersTrackies()
}