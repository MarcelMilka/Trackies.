package com.example.trackies.homeScreen.presentation

import com.example.trackies.homeScreen.buisness.LicenseViewState
import com.example.trackies.homeScreen.buisness.TrackieViewState

sealed class HomeScreenViewState {

    object Loading: HomeScreenViewState()

    data class LoadedSuccessfully(
        var license: LicenseViewState,
        var trackiesForToday: List<TrackieViewState>,
        var namesOfAllTrackies: List<String>,
        var allTrackies: List<TrackieViewState>?
    ): HomeScreenViewState()

    object FailedToLoadData: HomeScreenViewState()
}
