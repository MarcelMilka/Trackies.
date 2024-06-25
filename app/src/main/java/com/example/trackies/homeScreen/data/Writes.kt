package com.example.trackies.homeScreen.data

import com.example.trackies.homeScreen.buisness.TrackieViewState

interface Writes {

    suspend fun addNewTrackie(trackieViewState: TrackieViewState)
}