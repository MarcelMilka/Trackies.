package com.example.trackies.homeScreen.presentation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.homeScreen.data.HomeScreenRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val uniqueIdentifier: String): ViewModel() {

    private val repository = HomeScreenRepository(uniqueIdentifier = uniqueIdentifier)

    private var _uiState = MutableStateFlow<HomeScreenViewState>(HomeScreenViewState.Loading)
    val uiState: StateFlow<HomeScreenViewState> get() = _uiState.asStateFlow()

    init {
        repository.isFirstTimeInApp()

        viewModelScope.launch {

            _uiState.update { HomeScreenViewState.Loading }

            delay(5000)

            val licenseInformation = repository.fetchUsersLicenseInformation()
            val trackiesForToday = repository.fetchTrackiesForToday()

            if (licenseInformation != null && trackiesForToday != null ) {

                Log.d("ustaaa", "$trackiesForToday")
                _uiState.update {

                    HomeScreenViewState.LoadedSuccessfully(

                        license = licenseInformation,
                        trackies = trackiesForToday
                    )
                }
            }

            else { _uiState.update { HomeScreenViewState.FailedToLoadData } }
        }
    }
}