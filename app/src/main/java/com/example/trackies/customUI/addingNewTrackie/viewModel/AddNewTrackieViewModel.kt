package com.example.trackies.customUI.addingNewTrackie.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AddNewTrackieViewModel: ViewModel() {

    var viewState: MutableStateFlow<AddNewTrackieViewState> = MutableStateFlow(AddNewTrackieViewState())
    var activityState = MutableStateFlow(ActivityState())
    var buttonIsEnabled = MutableStateFlow(false)

    init {

        viewModelScope.launch {

            viewState.collect {

                Log.d("whatever it takes", "${it.measuringUnit}")

                if (

                    it.name != "" &&
                    it.totalDose != 0 &&
                    it.measuringUnit != "" &&
                    it.repeatOn.count() != 0
                ){ buttonIsEnabled.emit(true) }

                else { buttonIsEnabled.emit(false) }
            }
        }
    }

    fun updateName (nameOfTheNewTrackie: String) {

        viewState.update {
            it.copy(
                name = nameOfTheNewTrackie
            )
        }
    }

    fun updateMeasuringUnitAndDose(totalDose: Int, measuringUnit: String) {

        viewState.update {
            it.copy(
                totalDose = totalDose,
                measuringUnit = measuringUnit
            )
        }
    }

    fun updateRepeatOn(repeatOn: MutableList<String>) {

        viewState.update {
            it.copy(
                repeatOn = repeatOn
            )
        }
    }

    fun clearAll() {

        viewState.update {

            it.copy(

                name = "",
                totalDose = 0,
                repeatOn = mutableListOf<String>(),
                measuringUnit = "",
                ingestionTime = null
            )
        }

        activityState.update {

            it.copy(

                insertNameIsActive = false,
                insertTotalDoseIsActive = false,
                scheduleDaysIsActive = false,
                insertTimeOfIngestionIsActive = false
            )
        }
    }

    fun activate(whatToActivate: IsActive) {

        when (whatToActivate) {

            IsActive.NameOfTrackie -> {

                activityState.update { activityState ->
                    activityState.copy(
                        insertNameIsActive = true,
                        insertTotalDoseIsActive = false,
                        scheduleDaysIsActive = false,
                        insertTimeOfIngestionIsActive = false
                    )
                }
            }

            IsActive.DailyDosage -> {
                activityState.update { activityState ->
                    activityState.copy(
                        insertNameIsActive = false,
                        insertTotalDoseIsActive = true,
                        scheduleDaysIsActive = false,
                        insertTimeOfIngestionIsActive = false
                    )
                }
            }

            IsActive.ScheduleDays -> {
                activityState.update { activityState ->
                    activityState.copy(
                        insertNameIsActive = false,
                        insertTotalDoseIsActive = false,
                        scheduleDaysIsActive = true,
                        insertTimeOfIngestionIsActive = false
                    )
                }
            }

            IsActive.TimeOfIngestion -> {
                activityState.update { activityState ->
                    activityState.copy(
                        insertNameIsActive = false,
                        insertTotalDoseIsActive = false,
                        scheduleDaysIsActive = false,
                        insertTimeOfIngestionIsActive = true
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
                        insertNameIsActive = false,
                    )
                }
            }

            IsActive.DailyDosage -> {
                activityState.update { activityState ->
                    activityState.copy(
                        insertTotalDoseIsActive = false,
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
                        insertTimeOfIngestionIsActive = false
                    )
                }
            }
        }
    }
}

