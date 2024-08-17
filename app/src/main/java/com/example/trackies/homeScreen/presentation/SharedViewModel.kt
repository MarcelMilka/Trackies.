package com.example.trackies.homeScreen.presentation

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackies.DateTimeClass
import com.example.trackies.customUI.homeScreen.GraphToDisplay
import com.example.trackies.homeScreen.buisness.LicenseViewState
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.homeScreen.data.HomeScreenRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SharedViewModel(private val uniqueIdentifier: String): ViewModel() {

    private val dateTimeClass = DateTimeClass()

    private val repository = HomeScreenRepository(uniqueIdentifier = uniqueIdentifier)

    private var _uiState = MutableStateFlow<SharedViewModelViewState>(value = SharedViewModelViewState.Loading)
    private var _graphToDisplay = MutableStateFlow(value = GraphToDisplay.Weekly)
    private var _trackieToDisplay: MutableStateFlow<TrackieViewState?> = MutableStateFlow(value = null)
    private var _heightOfHomeScreenLazyColumn = MutableStateFlow(value = 195)
    val uiState: StateFlow<SharedViewModelViewState> get() = _uiState
    val graphToDisplay: StateFlow<GraphToDisplay> get() = _graphToDisplay
    val trackieToDisplay: StateFlow<TrackieViewState?> get() = _trackieToDisplay

    val heightOfHomeScreenLazyColumn: StateFlow<Int> get() = _heightOfHomeScreenLazyColumn

    init {

        repository.isFirstTimeInApp()

        viewModelScope.launch {

            delay(1000)

            val licenseInformation = repository.fetchUsersLicenseInformation()
            val trackiesForToday = repository.fetchTrackiesForToday()
            val namesOfAllTrackies = repository.fetchNamesOfAllTrackies()
            val statesOfTrackiesForToday = repository.fetchStatesOfTrackiesForToday() //mapOf("" to true) todo here's a bug
            val weeklyRegularity = repository.fetchWeeklyRegularity()

            if (
                licenseInformation != null &&
                trackiesForToday != null &&
                namesOfAllTrackies != null &&
                statesOfTrackiesForToday != null &&
                weeklyRegularity != null
                ) {

                delay(2500)

                _uiState.update {

                    SharedViewModelViewState.LoadedSuccessfully(

                        license = licenseInformation,
                        trackiesForToday = trackiesForToday,
                        namesOfAllTrackies = namesOfAllTrackies,
                        allTrackies = null,
                        statesOfTrackiesForToday = statesOfTrackiesForToday,
                        weeklyRegularity = weeklyRegularity
                    )
                }

                adjustHeightOfLazyColumn(
                    amountOfTrackiesToDisplay = (uiState.value as SharedViewModelViewState.LoadedSuccessfully).trackiesForToday.count(),
                    adjustedHeight = {_heightOfHomeScreenLazyColumn.value = it}
                )
            }

            else { _uiState.update { SharedViewModelViewState.FailedToLoadData } }
        }
    }

    fun addNewTrackie(trackieViewState: TrackieViewState) {

        val currentDayOfWeek = dateTimeClass.getCurrentDayOfWeek()

//      add new trackie in the database
        viewModelScope.launch { repository.addNewTrackie(trackieViewState = trackieViewState) }

//      add new trackie to the viewState

//          a copy of current viewState
            val copyOfViewState = _uiState.value as SharedViewModelViewState.LoadedSuccessfully

//          a copy of current licenseViewState
            val copyOfLicenseViewState = copyOfViewState.license.copy()

            val newAmountOfTrackies = copyOfViewState.license.totalAmountOfTrackies + 1

            val newLicenseViewState = LicenseViewState(
                active = copyOfLicenseViewState.active,
                validUntil = copyOfLicenseViewState.validUntil,
                totalAmountOfTrackies = newAmountOfTrackies
            )

            val newTrackiesForToday = copyOfViewState.trackiesForToday.toMutableList()
            if (trackieViewState.repeatOn.contains(currentDayOfWeek)) {

                newTrackiesForToday.add(trackieViewState)
            }

            val newNamesOfAllTrackies = copyOfViewState.namesOfAllTrackies.toMutableList()
            newNamesOfAllTrackies.add(trackieViewState.name)

            val newAllTrackies = copyOfViewState.allTrackies?.toMutableList()

            if (copyOfViewState.allTrackies != null) { newAllTrackies!!.add(trackieViewState) } else { null }

            val newStatesOfTrackiesForToday: MutableMap<String, Boolean> = mutableMapOf()

            copyOfViewState.statesOfTrackiesForToday.forEach {

                newStatesOfTrackiesForToday[it.key] = it.value
            }

            newStatesOfTrackiesForToday[trackieViewState.name] = false

            var newWeeklyRegularity = mutableMapOf<String, Map<Int, Int>>()
            var passedCurrentDayOfWeek = false


            copyOfViewState.weeklyRegularity.forEach { array ->

                if (trackieViewState.repeatOn.contains(array.key)) {


                    val total = array.value.keys.toIntArray()[0] + 1

                    val ingested =
                        when (passedCurrentDayOfWeek) {

                            true -> { 0 }

                            false -> {

                                if  (currentDayOfWeek == array.key) {

                                    passedCurrentDayOfWeek = true
                                    array.value.values.toIntArray()[0]
                                }

                                else { array.value.values.toIntArray()[0] + 1 }
                            }
                        }

                    newWeeklyRegularity.put(key = array.key, value = mapOf(total to ingested))
                }

                else {

                    val total = array.value.keys.toIntArray()[0]
                    val ingested = array.value.values.toIntArray()[0]

                    newWeeklyRegularity.put(key = array.key, value = mapOf(total to ingested))
                }
            }


        adjustHeightOfLazyColumn(
            amountOfTrackiesToDisplay = newTrackiesForToday.count(),
            adjustedHeight = {_heightOfHomeScreenLazyColumn.value = it}
        )

            _uiState.update {

                SharedViewModelViewState.LoadedSuccessfully(
                    license = newLicenseViewState,
                    trackiesForToday = newTrackiesForToday,
                    namesOfAllTrackies = newNamesOfAllTrackies,
                    allTrackies = newAllTrackies,
                    statesOfTrackiesForToday = newStatesOfTrackiesForToday,
                    weeklyRegularity = newWeeklyRegularity
                )
            }
    }

    fun checkTrackieAsIngestedForToday(trackieViewState: TrackieViewState) {

//      update state of a trackie in the database
        repository.checkTrackieAsIngestedForToday(trackieViewState)

//      update state of a trackie in the viewState

    //      a copy of current data
            val copyOfViewState = _uiState.value as SharedViewModelViewState.LoadedSuccessfully

            val updatedStatesOfTrackiesForToday: MutableMap<String, Boolean> = mutableMapOf()

            copyOfViewState.statesOfTrackiesForToday.forEach {

                if (it.key == trackieViewState.name) {
                    updatedStatesOfTrackiesForToday[it.key] = true
                }

                else {
                    updatedStatesOfTrackiesForToday[it.key] = it.value
                }
            }

            val updatedWeeklyRegularity = mutableMapOf<String, Map<Int, Int>>()
            val currentDayOfWeek = dateTimeClass.getCurrentDayOfWeek()

            copyOfViewState.weeklyRegularity.forEach { array ->

                if (array.key == currentDayOfWeek) {

                    val total = array.value.keys.toIntArray()[0]
                    val ingested = array.value.values.toIntArray()[0] + 1

                    updatedWeeklyRegularity.put(key = array.key, value = mapOf(total to ingested))
                }

                else {

                    val total = array.value.keys.toIntArray()[0]
                    val ingested = array.value.values.toIntArray()[0]

                    updatedWeeklyRegularity.put(key = array.key, value = mapOf(total to ingested))
                }
            }

            _uiState.update {

                SharedViewModelViewState.LoadedSuccessfully(
                    license = copyOfViewState.license,
                    trackiesForToday = copyOfViewState.trackiesForToday,
                    namesOfAllTrackies = copyOfViewState.namesOfAllTrackies,
                    allTrackies = copyOfViewState.allTrackies,
                    statesOfTrackiesForToday = updatedStatesOfTrackiesForToday,
                    weeklyRegularity = updatedWeeklyRegularity
                )
            }
    }

    fun fetchAllTrackies() {

        viewModelScope.launch {

            val allTrackies = repository.fetchAllTrackies()

            if (allTrackies != null) {

//              a copy of current data
                val copy = _uiState.value as SharedViewModelViewState.LoadedSuccessfully

                _uiState.update {

                    SharedViewModelViewState.LoadedSuccessfully(
                        license = copy.license,
                        trackiesForToday = copy.trackiesForToday,
                        namesOfAllTrackies = copy.namesOfAllTrackies,
                        allTrackies = allTrackies,
                        statesOfTrackiesForToday = copy.statesOfTrackiesForToday,
                        weeklyRegularity = copy.weeklyRegularity
                    )
                }
            }
        }
    }

    fun changeGraphToDisplay(chartToDisplay: GraphToDisplay) { _graphToDisplay.update { chartToDisplay } }
    fun setTrackieToDisplay(trackieToDisplay: TrackieViewState) { _trackieToDisplay.update { trackieToDisplay } }

    fun deleteTrackie(trackieToDelete: TrackieViewState) {

        val copyOfViewState = _uiState.value as SharedViewModelViewState.LoadedSuccessfully

        viewModelScope.launch {

//          Decrease the amount of trackies by 1:
            repository.decreaseAmountOfTrackies(
                onSuccess = { Log.d("SharedViewModel, deleteTrackie", "decreaseAmountOfTrackies: successfully finished") },
                onFailure = { Log.d("SharedViewModel, deleteTrackie", "decreaseAmountOfTrackies: $it") }
            )
            val newLicenseViewState = LicenseViewState(
                active = copyOfViewState.license.active,
                validUntil = copyOfViewState.license.validUntil,
                totalAmountOfTrackies = copyOfViewState.license.totalAmountOfTrackies - 1
            )

            repository.deleteTrackieFromNamesOfTrackies(
                trackieViewState = trackieToDelete,
                onSuccess = { Log.d("SharedViewModel, deleteTrackie", "deleteTrackieFromNamesOfTrackies: successfully finished") },
                onFailure = { Log.d("SharedViewModel, deleteTrackie", "deleteTrackieFromNamesOfTrackies: $it") }
            )
            val newTrackiesForToday = mutableListOf<TrackieViewState>()
            copyOfViewState.trackiesForToday.forEach { trackieViewState ->

                if (trackieViewState != trackieToDelete) { newTrackiesForToday.add(trackieViewState) }
            }

            val newNamesOfAllTrackies = mutableListOf<String>()
            copyOfViewState.namesOfAllTrackies.forEach { nameOfTrackie ->

                if (nameOfTrackie != trackieToDelete.name) { newNamesOfAllTrackies.add(nameOfTrackie) }
            }

            var newAllTrackies : MutableList<TrackieViewState>? = mutableListOf()
            if (copyOfViewState.allTrackies != null) {

                copyOfViewState.allTrackies!!.forEach { trackieViewState ->

                    if (trackieToDelete.name != trackieViewState.name ) {

                        newAllTrackies!!.add(trackieViewState)
                    }
                }
            }

            else { newAllTrackies = null }

            var newWeeklyRegularity = mutableMapOf<String, Map<Int, Int>>()
            val currentDayOfWeek = dateTimeClass.getCurrentDayOfWeek()
            var passedCurrentDayOfWeek = false

            listOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday").forEach { dayOfWeek ->

                if (trackieToDelete.repeatOn.contains(dayOfWeek)) {

                    val total = copyOfViewState.weeklyRegularity[dayOfWeek]!!.keys.toIntArray()[0] - 1
                    val ingested = when (passedCurrentDayOfWeek) {

                        true -> { 0 }

                        false -> {

                            if (currentDayOfWeek == dayOfWeek) {

                                passedCurrentDayOfWeek = true
                                copyOfViewState.statesOfTrackiesForToday[trackieToDelete.name].let { state ->

                                    if (state!!) { copyOfViewState.weeklyRegularity[dayOfWeek]!!.values.toIntArray()[0] - 1 }

                                    else { copyOfViewState.weeklyRegularity[dayOfWeek]!!.values.toIntArray()[0] }
                                }
                            }

                            else { copyOfViewState.weeklyRegularity[dayOfWeek]!!.values.toIntArray()[0] - 1 }
                        }
                    }

                    Log.d("panta rhei", "day of week: $dayOfWeek total: $total ingested: $ingested")
                    newWeeklyRegularity[dayOfWeek] = mapOf(total to ingested)
                }

                else {

                    val total = copyOfViewState.weeklyRegularity[dayOfWeek]!!.keys.toIntArray()[0]
                    val ingested = copyOfViewState.weeklyRegularity[dayOfWeek]!!.values.toIntArray()[0]
                    newWeeklyRegularity[dayOfWeek] = mapOf(total to ingested)
                }
            }

            Log.d("new weekly regularity", "$newWeeklyRegularity")


            repository.deleteTrackieFromUsersWeeklyStatistics(
                trackieViewState = trackieToDelete,
                onSuccess = { Log.d("SharedViewModel, deleteTrackie", "deleteTrackieFromUsersWeeklyStatistics: successfully finished") },
                onFailure = { Log.d("SharedViewModel, deleteTrackie", "deleteTrackieFromUsersWeeklyStatistics: $it") }
            )

            repository.deleteTrackieFromUsersTrackies(
                trackieViewState = trackieToDelete,
                onSuccess = { Log.d("SharedViewModel, deleteTrackie", "deleteTrackieFromUsersTrackies: successfully finished") },
                onFailure = { Log.d("SharedViewModel, deleteTrackie", "deleteTrackieFromUsersTrackies: $it") }
            )

            adjustHeightOfLazyColumn(
                amountOfTrackiesToDisplay = newTrackiesForToday.count(),
                adjustedHeight = {_heightOfHomeScreenLazyColumn.value = it}
            )

            _uiState.update {

                SharedViewModelViewState.LoadedSuccessfully(
                    license = newLicenseViewState,
                    trackiesForToday = newTrackiesForToday,
                    namesOfAllTrackies = newNamesOfAllTrackies,
                    allTrackies = newAllTrackies,
                    statesOfTrackiesForToday = copyOfViewState.statesOfTrackiesForToday,
                    weeklyRegularity = newWeeklyRegularity
                )
            }
        }
    }
}

private fun adjustHeightOfLazyColumn(amountOfTrackiesToDisplay: Int, adjustedHeight: (Int) -> Unit) {

    when (amountOfTrackiesToDisplay) {

        0 -> { adjustedHeight(0) }
        1 -> { adjustedHeight(60) }
        2 -> { adjustedHeight(125) }
        3 -> { adjustedHeight(195) }

        else -> { adjustedHeight(215) }
    }
}