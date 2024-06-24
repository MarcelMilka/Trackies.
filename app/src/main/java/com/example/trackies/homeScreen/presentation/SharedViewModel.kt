package com.example.trackies.homeScreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackies.homeScreen.data.HomeScreenRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SharedViewModel(private val uniqueIdentifier: String): ViewModel() {

    private val repository = HomeScreenRepository(uniqueIdentifier = uniqueIdentifier)

    private var _uiState = MutableStateFlow<HomeScreenViewState>(HomeScreenViewState.Loading)
    val uiState: StateFlow<HomeScreenViewState> get() = _uiState.asStateFlow()

    private var launchInit = true

    init {

        repository.isFirstTimeInApp()

        viewModelScope.launch {

            _uiState.update { HomeScreenViewState.Loading }

            val licenseInformation = repository.fetchUsersLicenseInformation()
            val trackiesForToday = repository.fetchTrackiesForToday()

            if (
                licenseInformation != null &&
                trackiesForToday != null
                ) {

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