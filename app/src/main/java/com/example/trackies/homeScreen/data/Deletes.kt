package com.example.trackies.homeScreen.data

import com.example.trackies.homeScreen.buisness.TrackieViewState

interface DeleteTrackie {

    suspend fun decreaseAmountOfTrackies(onSuccess: () -> Unit, onFailure: (String) -> Unit)

    suspend fun deleteTrackieFromNamesOfTrackies(trackieViewState: TrackieViewState, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    suspend fun deleteTrackieFromUsersWeeklyStatistics(trackieViewState: TrackieViewState, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    suspend fun deleteTrackieFromUsersTrackies(trackieViewState: TrackieViewState, onSuccess: () -> Unit, onFailure: (String) -> Unit)
}