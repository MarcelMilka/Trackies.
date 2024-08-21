package com.example.trackies.detailedTrackie.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackies.detailedTrackie.data.DetailedTrackieRepository
import com.example.trackies.homeScreen.buisness.TrackieViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailedTrackieViewModel( uniqueIdentifier: String ): ViewModel() {

    private val repository = DetailedTrackieRepository(uniqueIdentifier = uniqueIdentifier)
    private val _uiState = MutableStateFlow<DetailedTrackieViewState>(DetailedTrackieViewState.Loading)
    private val _trackieToDisplay = MutableStateFlow<TrackieViewState?>(null)

    val uiState: MutableStateFlow<DetailedTrackieViewState> = _uiState
    val trackieToDisplay: MutableStateFlow<TrackieViewState?> = _trackieToDisplay

    fun setTrackieToDisplayDetails( trackieViewState: TrackieViewState ) { _trackieToDisplay.value = trackieViewState }

    fun calculateWeeklyRegularity( trackieViewState: TrackieViewState ) {

        viewModelScope.launch {

            if (_uiState.value == DetailedTrackieViewState.Loading || _uiState.value == DetailedTrackieViewState.FailedToLoadData) {

                val weeklyRegularityOfTheTrackie = repository.fetchWeeklyRegularityOfTheTrackie( trackieViewState = trackieViewState )

                if (weeklyRegularityOfTheTrackie != null) {

                    _uiState.update {

                        DetailedTrackieViewState.SucceededToLoadData(

                            trackiesWithWeeklyRegularity = weeklyRegularityOfTheTrackie
                        )
                    }
                }

                else {

                    _uiState.update {

                        DetailedTrackieViewState.FailedToLoadData
                    }
                }
            }

            else {

                val weeklyRegularity = _uiState.value as DetailedTrackieViewState.SucceededToLoadData

                if (!weeklyRegularity.trackiesWithWeeklyRegularity.keys.contains(trackieViewState.name)) {

                    _uiState.update {

                        DetailedTrackieViewState.Loading
                    }

                    val weeklyRegularityOfTheTrackie: MutableMap<String, Map<String, Int>>? = repository.fetchWeeklyRegularityOfTheTrackie( trackieViewState = trackieViewState )

                    if (weeklyRegularityOfTheTrackie != null) {

                        val key = weeklyRegularityOfTheTrackie.keys.toList()[0]
                        val value = weeklyRegularityOfTheTrackie.values.toList()[0]

                        val updatedWeeklyRegularity: MutableMap<String, Map<String, Int>> = mutableMapOf()

                        weeklyRegularity.trackiesWithWeeklyRegularity
                            .forEach { map ->
                                updatedWeeklyRegularity[map.key] = map.value
                            }
                            .also { updatedWeeklyRegularity[key] = value }

                        _uiState.update {

                            DetailedTrackieViewState.SucceededToLoadData(

                                trackiesWithWeeklyRegularity = updatedWeeklyRegularity
                            )
                        }
                    }

                    else {

                        _uiState.update {

                            DetailedTrackieViewState.FailedToLoadData
                        }
                    }
                }
            }
        }
    }
}