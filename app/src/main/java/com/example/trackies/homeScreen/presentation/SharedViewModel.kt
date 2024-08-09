package com.example.trackies.homeScreen.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.homeScreen.data.HomeScreenRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SharedViewModel(private val uniqueIdentifier: String): ViewModel() {

    private val repository = HomeScreenRepository(uniqueIdentifier = uniqueIdentifier)

    private var _uiState = MutableStateFlow<SharedViewModelViewState>(SharedViewModelViewState.Loading)
    val uiState: StateFlow<SharedViewModelViewState> get() = _uiState

    init {

        repository.isFirstTimeInApp()

        viewModelScope.launch {

            delay(1000)

            val licenseInformation = repository.fetchUsersLicenseInformation()
            val trackiesForToday = repository.fetchTrackiesForToday()
            val namesOfAllTrackies = repository.fetchNamesOfAllTrackies()
            val statesOfTrackiesForToday = repository.fetchStatesOfTrackiesForToday()

            if ( licenseInformation != null && trackiesForToday != null && namesOfAllTrackies != null && statesOfTrackiesForToday != null ) {

                Log.d("GTR-R35", "$licenseInformation")
                Log.d("GTR-R35", "$trackiesForToday")
                Log.d("GTR-R35", "$namesOfAllTrackies")
                Log.d("SharedViewModel, init, statesOfTrackiesForToday", "$statesOfTrackiesForToday")

                delay(2500)

                _uiState.update {

                    SharedViewModelViewState.LoadedSuccessfully(

                        license = licenseInformation,
                        trackiesForToday = trackiesForToday,
                        namesOfAllTrackies = namesOfAllTrackies,
                        allTrackies = null,
                        statesOfTrackiesForToday = statesOfTrackiesForToday
                    )
                }
            }

            else { _uiState.update { SharedViewModelViewState.FailedToLoadData } }
        }
    }

    fun addNewTrackie(trackieViewState: TrackieViewState) {

        viewModelScope.launch { repository.addNewTrackie(trackieViewState = trackieViewState) }
    }

    fun checkTrackieAsIngestedForToday(trackieViewState: TrackieViewState) {

//      update state of a trackie in the database
        repository.checkTrackieAsIngestedForToday(trackieViewState)

//      update state of a trackie in the viewState

    //      a copy of current data
            val copyOfViewState = _uiState.value as SharedViewModelViewState.LoadedSuccessfully

            val updatedStatesOfTrackiesForToday: MutableMap<String, Boolean> = mutableMapOf()

            copyOfViewState.statesOfTrackiesForToday.forEach {

                if (it.key == trackieViewState.name) {
                    updatedStatesOfTrackiesForToday[it.key] = true
                }

                else {
                    updatedStatesOfTrackiesForToday[it.key] = it.value
                }
            }

            _uiState.update {

                SharedViewModelViewState.LoadedSuccessfully(
                    license = copyOfViewState.license,
                    trackiesForToday = copyOfViewState.trackiesForToday,
                    namesOfAllTrackies = copyOfViewState.namesOfAllTrackies,
                    allTrackies = copyOfViewState.allTrackies,
                    statesOfTrackiesForToday = updatedStatesOfTrackiesForToday
                )
            }
    }

    fun fetchAllTrackies() {

        viewModelScope.launch {

            val allTrackies = repository.fetchAllTrackies()

            if (allTrackies != null) {

//              a copy of current data
                val copy = _uiState.value as SharedViewModelViewState.LoadedSuccessfully

                _uiState.update {

                    SharedViewModelViewState.LoadedSuccessfully(
                        license = copy.license,
                        trackiesForToday = copy.trackiesForToday,
                        namesOfAllTrackies = copy.namesOfAllTrackies,
                        allTrackies = allTrackies,
                        statesOfTrackiesForToday = copy.statesOfTrackiesForToday
                    )
                }
            }
        }
    }
}