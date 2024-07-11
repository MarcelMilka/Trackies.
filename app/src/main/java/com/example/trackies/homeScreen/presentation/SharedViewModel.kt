package com.example.trackies.homeScreen.presentation

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.homeScreen.data.HomeScreenRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SharedViewModel(private val uniqueIdentifier: String): ViewModel() {

    private val repository = HomeScreenRepository(uniqueIdentifier = uniqueIdentifier)

    private var _uiState = MutableStateFlow<HomeScreenViewState>(HomeScreenViewState.Loading)
    val uiState: StateFlow<HomeScreenViewState> get() = _uiState

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

                    HomeScreenViewState.LoadedSuccessfully(

                        license = licenseInformation,
                        trackies = trackiesForToday,
                        namesOfAllTrackies = namesOfAllTrackies
                    )
                }
            }

            else { _uiState.update { HomeScreenViewState.FailedToLoadData } }
        }
    }

    fun addNewTrackie(trackieViewState: TrackieViewState) {

        viewModelScope.launch { repository.addNewTrackie(trackieViewState = trackieViewState) }
    }
}