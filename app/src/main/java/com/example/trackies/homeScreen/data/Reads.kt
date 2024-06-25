package com.example.trackies.homeScreen.data

import com.example.trackies.homeScreen.buisness.LicenseViewState
import com.example.trackies.homeScreen.buisness.TrackieViewState


interface Reads {

    fun isFirstTimeInApp()

    suspend fun fetchUsersLicenseInformation(): LicenseViewState?

    suspend fun fetchTrackiesForToday(): List<TrackieViewState>?

    suspend fun fetchNamesOfAllTrackies(): List<String>?
}