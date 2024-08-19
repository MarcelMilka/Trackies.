package com.example.trackies.detailedTrackie

sealed class DetailedTrackieViewState {

    object Loading: DetailedTrackieViewState()

    data class LoadedSuccessfully(var trackiesWithFetchedStatistics: MutableMap<String, Map<String, Int>>): DetailedTrackieViewState()

    object FailedToLoadData: DetailedTrackieViewState()
}
