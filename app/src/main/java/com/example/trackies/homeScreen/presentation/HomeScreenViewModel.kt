package com.example.trackies.homeScreen.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackies.homeScreen.buisness.LicenseViewState
import com.example.trackies.homeScreen.data.HomeScreenRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val uniqueIdentifier: String): ViewModel() {

    private val repository = HomeScreenRepository(uniqueIdentifier = uniqueIdentifier)

    private var _uiState = MutableStateFlow<HomeScreenViewState>(HomeScreenViewState.Loading)
    val uiState: StateFlow<HomeScreenViewState> get() = _uiState.asStateFlow()

    init {
        repository.isFirstTimeInApp()

        viewModelScope.launch {

            val licenseInformation = LicenseViewState(false, null, 0)
            val trackiesForToday = repository.fetchTrackiesForToday()

            Log.d("halla!", "tastyyy $licenseInformation")
            Log.d("halla!", "tastyyy $trackiesForToday")

            if (licenseInformation != null && trackiesForToday != null) {

                _uiState.update {

                    HomeScreenViewState.LoadedSuccessfully(

                        license = licenseInformation,
                        trackies = trackiesForToday
                    )
                }
            }

            else {
                _uiState.update { HomeScreenViewState.FailedToLoadData }
            }
        }
    }
}