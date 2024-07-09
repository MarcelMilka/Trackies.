package com.example.trackies.customUI.addingNewTrackie.viewModel

data class ActivityState(
    var insertNameIsActive: Boolean = false,
    var insertTotalDoseIsActive: Boolean = false,
    var scheduleDaysIsActive: Boolean = false,
    var insertTimeOfIngestionIsActive: Boolean = false,
)
