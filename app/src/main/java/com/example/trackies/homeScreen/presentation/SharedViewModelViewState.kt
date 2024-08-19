package com.example.trackies.homeScreen.presentation

import com.example.trackies.homeScreen.buisness.LicenseViewState
import com.example.trackies.homeScreen.buisness.TrackieViewState

sealed class SharedViewModelViewState {

    object Loading: SharedViewModelViewState()

    data class LoadedSuccessfully(
        var license: LicenseViewState,
        var trackiesForToday: List<TrackieViewState>,
        var namesOfAllTrackies: List<String>,
        var allTrackies: List<TrackieViewState>?,
        var statesOfTrackiesForToday: Map<String,Boolean>,
        var weeklyRegularity: Map<String, Map<Int, Int>>,
        var weeklyRegularityOfParticularTrackies: Map<String, Map<String, Int>>?
    ): SharedViewModelViewState()

    object FailedToLoadData: SharedViewModelViewState()
}