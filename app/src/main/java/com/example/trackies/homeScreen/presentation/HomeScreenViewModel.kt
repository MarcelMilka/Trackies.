package com.example.trackies.homeScreen.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackies.homeScreen.data.HomeScreenRepository

class HomeScreenViewModel(private val uniqueIdentifier: String): ViewModel() {

    private val repository = HomeScreenRepository(uniqueIdentifier = uniqueIdentifier)

    init {
        Log.d("halla!", "init started working")
        repository.isFirstTimeInApp()
    }


}