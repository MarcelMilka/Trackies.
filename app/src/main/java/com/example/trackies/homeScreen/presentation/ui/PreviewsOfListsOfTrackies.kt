package com.example.trackies.homeScreen.presentation.ui

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.sharedComponentsOfUi.trackie.LoadingTrackie
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.sharedComponentsOfUi.trackie.Trackie
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.homeScreen.presentation.SharedViewModelViewState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PreviewOfListOfTrackiesLoadedSuccessfully(
    heightOfHomeScreenLazyColumn: StateFlow<Int>,
    uiState: SharedViewModelViewState.LoadedSuccessfully,

    onCheck: (trackieViewState: TrackieViewState) -> Unit,
    onDisplayDetails: (trackieViewState: TrackieViewState) -> Unit
) {

    val targetHeightOfLazyColumn = heightOfHomeScreenLazyColumn.collectAsState().value
    val heightOfLazyColumn by animateIntAsState(
        targetValue = targetHeightOfLazyColumn,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 50,
            easing = LinearOutSlowInEasing
        ),
        label = "adjust height of the lazy column",
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(heightOfLazyColumn.dp),

        verticalArrangement = Arrangement.Top,

        content = {

            items( uiState.trackiesForToday.take(3) ) { trackie ->

                Trackie(
                    name = trackie.name,
                    totalDose = trackie.totalDose,
                    measuringUnit = trackie.measuringUnit,
                    repeatOn = trackie.repeatOn,
                    ingestionTime = trackie.ingestionTime,
                    stateOfTheTrackie = uiState.statesOfTrackiesForToday[trackie.name]!!,
                    onCheck = { onCheck(trackie) },
                    onDisplayDetails = { onDisplayDetails(trackie) }
                )

                Spacer5()
            }

            item {

                if (uiState.trackiesForToday.count() > 3) {

                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp),

                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,

                        content = {

                            val amountOfTrackiesLeft = uiState.trackiesForToday.count() - 3

                            if (amountOfTrackiesLeft == 1) {
                                TextSmall(content = "+ $amountOfTrackiesLeft more trackie")
                            }

                            else {
                                TextSmall(content = "+ $amountOfTrackiesLeft more trackies")
                            }
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun PreviewOfListOfTrackiesLoading() {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp),

        verticalArrangement = Arrangement.Top,

        content = {

            this.item {

                LoadingTrackie()
                Spacer5()
                LoadingTrackie()
                Spacer5()
                LoadingTrackie()
            }
        }
    )
}