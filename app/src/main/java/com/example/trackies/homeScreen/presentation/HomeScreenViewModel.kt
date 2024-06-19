package com.example.trackies.homeScreen.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackies.homeScreen.data.HomeScreenRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val uniqueIdentifier: String): ViewModel() {

    private val repository = HomeScreenRepository(uniqueIdentifier = uniqueIdentifier)

    private val _uiState = MutableStateFlow<HomeScreenViewState>(HomeScreenViewState.Loading)
    val uiState: StateFlow<HomeScreenViewState> get() = _uiState.asStateFlow()

    init {
        Log.d("halla!", "init started working")
        repository.isFirstTimeInApp()

        loadData()
    }

    fun loadData() {

        viewModelScope.launch {

            _uiState.update { HomeScreenViewState.Loading }

            val usersInformation = repository.fetchUsersInformation()
            val usersTrackies = repository.fetchUsersTrackies()
            val usersStatistics = repository.fetchUsersStatistics()

            if (usersInformation != null) {

                _uiState.update {
                    (HomeScreenViewState.LoadedSuccessfully(
                        license = usersInformation,
                        trackies = usersTrackies,
                        statistics = usersStatistics)
                    )
                }
            }

            else { _uiState.update { HomeScreenViewState.FailedToLoadData } }
        }
    }
}