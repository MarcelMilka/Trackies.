package com.example.trackies.homeScreen.buisness.entities

data class TrackieViewStateEntity(
    val name: String? = null,
    val totalDose: Int? = null,
    val measuringUnit: String? = null,
    val repeatOn: List<String>? = null,
    val ingestionTime: Map<String, Int>? = null
)