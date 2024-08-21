package com.example.trackies.showAllTrackies.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.sharedComponentsOfUi.trackie.Trackie

@Composable
fun DisplayAllTrackiesForToday(
    listOfTrackies: List<TrackieViewState>,
    statesOfTrackiesForToday: Map<String,Boolean>,
    onCheck: (TrackieViewState) -> Unit,
    onDisplayDetails: (TrackieViewState) -> Unit,
) {

    LazyColumn(

        modifier = Modifier
            .fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,

        content = {

            items(listOfTrackies) {trackieViewState ->

                Trackie(

                    name = trackieViewState.name,
                    totalDose = trackieViewState.totalDose,
                    measuringUnit = trackieViewState.measuringUnit,
                    repeatOn = trackieViewState.repeatOn,
                    ingestionTime = trackieViewState.ingestionTime,
                    stateOfTheTrackie = statesOfTrackiesForToday[trackieViewState.name]!!,
                    onCheck = { onCheck(trackieViewState) },
                    onDisplayDetails = { onDisplayDetails(trackieViewState) }
                )

                Spacer5()
            }
        }
    )
}