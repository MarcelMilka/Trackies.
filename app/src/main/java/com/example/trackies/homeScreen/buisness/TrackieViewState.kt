package com.example.trackies.homeScreen.buisness

data class TrackieViewState(
    val name: String,
    val totalDose: Int,
    val measuringUnit: TrackieUnits,
    val repeatOn: List<String>,
    val ingestionTime: Map<String, Int>?
)

enum class TrackieUnits {
    ml,
    g,
    pcs
}