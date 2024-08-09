package com.example.trackies.customUI.lazyColumns

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.homeScreen.LoadingTrackie
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.customUI.trackie.Trackie
import com.example.trackies.homeScreen.buisness.LicenseViewState
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.homeScreen.presentation.SharedViewModelViewState

@Composable fun HomeScreenLazyColumn(
    uiState: SharedViewModelViewState,
    onAddNewTrackie: (LicenseViewState) -> Unit,

    onCheck: (trackieViewState: TrackieViewState) -> Unit
) {

    var targetHeightOfLazyColumn by remember { mutableIntStateOf(195) }
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

            when (uiState) {

                SharedViewModelViewState.Loading -> {

                    this.item {

                        AnimatedVisibility(

                            visible = true,
                            enter = fadeIn(animationSpec = tween(500)),
                            exit = fadeOut(animationSpec = tween(500)),


                            content = {  }
                        )
                        LoadingTrackie()
                        Spacer5()
                        LoadingTrackie()
                        Spacer5()
                        LoadingTrackie()
                    }
                }

                is SharedViewModelViewState.LoadedSuccessfully -> {

                    targetHeightOfLazyColumn = when (uiState.trackiesForToday.count()) {

                        0 -> 0
                        1 -> 60
                        2 -> 125
                        3 -> 195

                        else -> 215
                    }

                    items( uiState.trackiesForToday.take(3) ) { trackie ->

                        Trackie(
                            name = trackie.name,
                            totalDose = trackie.totalDose,
                            measuringUnit = trackie.measuringUnit,
                            repeatOn = trackie.repeatOn,
                            ingestionTime = trackie.ingestionTime,
                            stateOfTheTrackie = uiState.statesOfTrackiesForToday[trackie.name]!!,
                            onCheck = { onCheck(trackie) }
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

                                    var amountOfTrackiesLeft = uiState.trackiesForToday.count() - 3

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

                SharedViewModelViewState.FailedToLoadData -> {

                    targetHeightOfLazyColumn = 100

                    this.item {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),

                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,

                            content = {

                                TextMedium("An error occurred while loading data.")
                            }
                        )
                    }
                }
            }
        }
    )
}