package com.example.trackies.detailedTrackie.data

import com.example.trackies.detailedTrackie.buisness.TrackieWithWeeklyRegularity
import com.example.trackies.homeScreen.buisness.TrackieViewState

interface Reads {

    suspend fun fetchWeeklyRegularityOfTheTrackie(trackieViewState: TrackieViewState): TrackieWithWeeklyRegularity?
}