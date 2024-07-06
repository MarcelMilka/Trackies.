package com.example.trackies.customUI.addingNewTrackie.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

class AddNewTrackieViewModel: ViewModel() {

    var activityState = MutableStateFlow(ActivityState())
    var viewState: MutableStateFlow<AddNewTrackieViewState> = MutableStateFlow(AddNewTrackieViewState())

    fun updateName (nameOfTheNewTrackie: String) {

        viewState.update {
            it.copy(
                nameOfTheNewTrackie = nameOfTheNewTrackie
            )
        }
    }

    fun clearAll() {

        viewState.update {
            it.copy(
                nameOfTheNewTrackie = ""
            )
        }
    }

    fun activate(whatToActivate: IsActive) {

        when (whatToActivate) {

            IsActive.NameOfTrackie -> {

                activityState.update { activityState ->
                    activityState.copy(
                        nameOfTrackieIsActive = true,
                        dailyDosageIsActive = false,
                        scheduleDaysIsActive = false,
                        timeOfIngestionIsActive = false
                    )
                }
            }

            IsActive.DailyDosage -> {
                activityState.update { activityState ->
                    activityState.copy(
                        nameOfTrackieIsActive = false,
                        dailyDosageIsActive = true,
                        scheduleDaysIsActive = false,
                        timeOfIngestionIsActive = false
                    )
                }
            }

            IsActive.ScheduleDays -> {
                activityState.update { activityState ->
                    activityState.copy(
                        nameOfTrackieIsActive = false,
                        dailyDosageIsActive = false,
                        scheduleDaysIsActive = true,
                        timeOfIngestionIsActive = false
                    )
                }
            }

            IsActive.TimeOfIngestion -> {
                activityState.update { activityState ->
                    activityState.copy(
                        nameOfTrackieIsActive = false,
                        dailyDosageIsActive = false,
                        scheduleDaysIsActive = false,
                        timeOfIngestionIsActive = true
                    )
                }
            }
        }
    }

    fun deActivate(whatToDeactivate: IsActive) {

        when (whatToDeactivate) {
            IsActive.NameOfTrackie -> {
                activityState.update { activityState ->
                    activityState.copy(
                        nameOfTrackieIsActive = false,
                    )
                }
            }

            IsActive.DailyDosage -> {
                activityState.update { activityState ->
                    activityState.copy(
                        dailyDosageIsActive = false,
                    )
                }
            }

            IsActive.ScheduleDays -> {
                activityState.update { activityState ->
                    activityState.copy(
                        scheduleDaysIsActive = false,
                    )
                }
            }

            IsActive.TimeOfIngestion -> {
                activityState.update { activityState ->
                    activityState.copy(
                        timeOfIngestionIsActive = false
                    )
                }
            }
        }
    }

    fun deactivateAll() {

        activityState.update { activityState ->
            activityState.copy(
                nameOfTrackieIsActive = false,
                dailyDosageIsActive = false,
                scheduleDaysIsActive = false,
                timeOfIngestionIsActive = false
            )
        }
    }
}

