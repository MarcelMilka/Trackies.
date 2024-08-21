package com.example.trackies.detailedTrackie.data

import com.example.trackies.homeScreen.buisness.TrackieViewState

interface Reads {

    suspend fun fetchWeeklyRegularityOfTheTrackie(trackieViewState: TrackieViewState): MutableMap<String, Map<String, Int>>?
}