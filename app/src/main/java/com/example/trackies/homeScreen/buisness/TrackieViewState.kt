package com.example.trackies.homeScreen.buisness

data class TrackieViewState(
    val name: String? = "",
    val totalDose: Int? = 0,
    val measuringUnit: String? = "",
    val repeatOn: List<String>? = listOf(),
    val ingestionTime: Map<String, Int>? = mutableMapOf<String, Int>()
)

enum class TrackieUnits {
    ml,
    g,
    pcs
}