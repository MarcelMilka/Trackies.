package com.example.trackies.detailedTrackie.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackies.DateTimeClass
import com.example.trackies.authentication.repository.FirebaseAuthentication
import com.example.trackies.detailedTrackie.buisness.TrackieWithWeeklyRegularity
import com.example.trackies.detailedTrackie.data.DetailedTrackieRepository
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.google.type.DateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailedTrackieViewModel(): ViewModel() {

    val uniqueIdentifier = FirebaseAuthentication().getSignedInUser().also { Log.d("ViewModel", "$it") }

    private val repository = DetailedTrackieRepository(uniqueIdentifier = uniqueIdentifier!!)
    private val _uiState = MutableStateFlow<DetailedTrackieViewState>(DetailedTrackieViewState.Loading)
    private val _trackieToDisplay = MutableStateFlow<TrackieViewState?>(null)

    val uiState: MutableStateFlow<DetailedTrackieViewState> = _uiState
    val trackieToDisplay: MutableStateFlow<TrackieViewState?> = _trackieToDisplay

    fun setTrackieToDisplayDetails( trackieViewState: TrackieViewState ) { _trackieToDisplay.value = trackieViewState }

    fun calculateWeeklyRegularity( trackieViewState: TrackieViewState ) {

        viewModelScope.launch {

            if (_uiState.value == DetailedTrackieViewState.Loading || _uiState.value == DetailedTrackieViewState.FailedToLoadData) {

                val trackieWithWeeklyRegularity = repository.fetchWeeklyRegularityOfTheTrackie( trackieViewState = trackieViewState )

                if (trackieWithWeeklyRegularity != null) {

                    _uiState.update {

                        DetailedTrackieViewState.SucceededToLoadData(

                            trackiesWithWeeklyRegularity = mutableListOf(trackieWithWeeklyRegularity)
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

                val listOfTrackiesWithWeeklyRegularity = (_uiState.value as DetailedTrackieViewState.SucceededToLoadData).trackiesWithWeeklyRegularity

                var alreadyExists = false

                listOfTrackiesWithWeeklyRegularity.forEach { trackieWithWeeklyRegularity ->

                    if (trackieWithWeeklyRegularity.name == trackieViewState.name) { alreadyExists = true }
                }

                if (!alreadyExists) {

                    _uiState.update {

                        DetailedTrackieViewState.Loading
                    }

                    val trackieWithWeeklyRegularity = repository.fetchWeeklyRegularityOfTheTrackie( trackieViewState = trackieViewState )

                    if (trackieWithWeeklyRegularity != null) {

                        listOfTrackiesWithWeeklyRegularity.add(trackieWithWeeklyRegularity)

                        _uiState.update {

                            DetailedTrackieViewState.SucceededToLoadData(trackiesWithWeeklyRegularity = listOfTrackiesWithWeeklyRegularity)
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

    fun updateWeeklyRegularityOfTheTrackie (trackieViewState: TrackieViewState) {

        when (_uiState.value) {

            DetailedTrackieViewState.Loading -> {}

            is DetailedTrackieViewState.SucceededToLoadData -> {

                val copyOfTrackiesWithWeeklyRegularity = (_uiState.value as DetailedTrackieViewState.SucceededToLoadData).trackiesWithWeeklyRegularity
                val trackieWithWeeklyRegularity = mutableListOf<TrackieWithWeeklyRegularity>()

                copyOfTrackiesWithWeeklyRegularity.forEach {

                    if (it.name == trackieViewState.name) {

                        val currentDayOfWeek = DateTimeClass().getCurrentDayOfWeek()

                        val name = it.name
                        val regularity = mutableMapOf<String, Int>()

                        it.regularity.forEach {map ->

                            if (map.key == currentDayOfWeek) { regularity[map.key] = 100 }

                            else { regularity[map.key] = map.value }
                        }

                        trackieWithWeeklyRegularity.add(TrackieWithWeeklyRegularity(name = name, regularity = regularity))
                    }

                    else { trackieWithWeeklyRegularity.add(it) }
                }

                _uiState.update {

                    DetailedTrackieViewState.SucceededToLoadData(trackieWithWeeklyRegularity)
                }
            }

            DetailedTrackieViewState.FailedToLoadData -> {}
        }
    }
}