package com.example.trackies.homeScreen.buisness

data class LicenseViewState(
    val active: Boolean = false,
    val validUntil: String? = null,
    var totalAmountOfTrackies: Int = 0
)
