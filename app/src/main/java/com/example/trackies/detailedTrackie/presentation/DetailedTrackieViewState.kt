package com.example.trackies.detailedTrackie.presentation

import com.example.trackies.detailedTrackie.buisness.TrackieWithWeeklyRegularity

sealed class DetailedTrackieViewState {

    object Loading: DetailedTrackieViewState()

    data class SucceededToLoadData(

        var trackiesWithWeeklyRegularity: MutableList<TrackieWithWeeklyRegularity>
    ): DetailedTrackieViewState()

    object FailedToLoadData: DetailedTrackieViewState()
}