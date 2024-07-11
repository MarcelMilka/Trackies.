package com.example.trackies.customUI.addingNewTrackie.viewModel

data class AddNewTrackieViewState(
    var name: String = "",
    var totalDose: Int = 0,
    var measuringUnit: String = "",
    var repeatOn: MutableList<String> = mutableListOf<String>(),
    var ingestionTime: MutableMap<String, Int>? = null
)
