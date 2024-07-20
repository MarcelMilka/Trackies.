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

            if ( licenseInformation != null && trackiesForToday != null && namesOfAllTrackies != null ) {

                Log.d("GTR-R35", "$licenseInformation")
                Log.d("GTR-R35", "$trackiesForToday")
                Log.d("GTR-R35", "$namesOfAllTrackies")

                delay(2500)

                _uiState.update {

                    SharedViewModelViewState.LoadedSuccessfully(

                        license = licenseInformation,
                        trackiesForToday = trackiesForToday,
                        namesOfAllTrackies = namesOfAllTrackies,
                        allTrackies = null
                    )
                }
            }

            else { _uiState.update { SharedViewModelViewState.FailedToLoadData } }
        }
    }

    fun addNewTrackie(trackieViewState: TrackieViewState) {

        viewModelScope.launch { repository.addNewTrackie(trackieViewState = trackieViewState) }
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
                        allTrackies = allTrackies
                    )
                }
            }
        }
    }
}