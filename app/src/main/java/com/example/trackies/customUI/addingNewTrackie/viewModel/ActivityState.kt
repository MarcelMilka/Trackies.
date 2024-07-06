package com.example.trackies.customUI.addingNewTrackie.viewModel

data class ActivityState(
    var nameOfTrackieIsActive: Boolean = false,
    var dailyDosageIsActive: Boolean = false,
    var scheduleDaysIsActive: Boolean = false,
    var timeOfIngestionIsActive: Boolean = false,
)
