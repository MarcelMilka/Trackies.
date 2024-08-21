package com.example.trackies.detailedTrackie.presentation

sealed class DetailedTrackieViewState {

    object Loading: DetailedTrackieViewState()

    data class SucceededToLoadData(

        var trackiesWithWeeklyRegularity: MutableMap<String, Map<String, Int>>
    ): DetailedTrackieViewState()

    object FailedToLoadData: DetailedTrackieViewState()
}
