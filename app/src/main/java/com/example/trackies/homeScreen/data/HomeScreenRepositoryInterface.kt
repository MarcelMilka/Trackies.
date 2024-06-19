package com.example.trackies.homeScreen.data

import com.example.trackies.homeScreen.buisness.LicenseViewState
import com.example.trackies.homeScreen.buisness.TrackieViewState


interface HomeScreenRepositoryInterface {

    fun isFirstTimeInApp()

    fun addNewUser()

    suspend fun fetchUsersInformation(): LicenseViewState?

    suspend fun fetchUsersTrackies(): List<TrackieViewState>?
    suspend fun fetchUsersStatistics(): List<String>?

}