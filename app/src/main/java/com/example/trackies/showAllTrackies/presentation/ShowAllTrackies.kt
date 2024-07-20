package com.example.trackies.showAllTrackies.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardReturn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.buttons.IconButtonToNavigateBetweenActivities
import com.example.trackies.customUI.buttons.MediumRadioTextButton
import com.example.trackies.customUI.spacers.Spacer40
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.MediumHeader
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.customUI.trackie.Trackie
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.homeScreen.presentation.SharedViewModelViewState
import com.example.trackies.ui.theme.BackgroundColor

@Composable
fun ShowAllTrackies(
    uiState: SharedViewModelViewState,
    onReturn: () -> Unit,
    fetchAllUsersTrackies: () -> Unit
) {

    var wholeWeek by remember { mutableStateOf(false) }
    var today by remember { mutableStateOf(true) }

    var whatToDisplay by remember { mutableStateOf(WhatToDisplay.TrackiesForToday) }

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor),

        content = {

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),

                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,

                content = {

                    IconButtonToNavigateBetweenActivities(icon = Icons.Rounded.KeyboardReturn) { onReturn() }

                    Spacer40()

                    MediumHeader(content = "All your trackies")

                    Spacer5()

                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp),

                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,

                        content = {

                            MediumRadioTextButton(text = "whole week", isSelected = wholeWeek) {

                                wholeWeek = it
                                today = !it

                                whatToDisplay = WhatToDisplay.TrackiesForTheWholeWeek
                            }

                            MediumRadioTextButton(text = "today", isSelected = today) {

                                today = it
                                wholeWeek = !it

                                whatToDisplay = WhatToDisplay.TrackiesForToday
                            }
                        }
                    )

                    Spacer5()

                    when (uiState) {

                        SharedViewModelViewState.Loading -> {}

                        is SharedViewModelViewState.LoadedSuccessfully -> {

                            when(whatToDisplay) {
                                WhatToDisplay.TrackiesForToday -> {

                                    ShowAllTrackiesLazyColumn(uiState.trackiesForToday)
                                }

                                WhatToDisplay.TrackiesForTheWholeWeek -> {

                                    when(uiState.allTrackies) {

                                        null -> {

                                            TextMedium("loading")
                                            fetchAllUsersTrackies()
//                                            uiState.fetchAllTrackies()
                                        }

                                        else -> {

                                            ShowAllTrackiesLazyColumn(uiState.allTrackies!!)
                                        }
                                    }
                                }
                            }
                        }

                        SharedViewModelViewState.FailedToLoadData -> {}
                    }
                }
            )
        }
    )
}

enum class WhatToDisplay {
    TrackiesForTheWholeWeek,
    TrackiesForToday
}

@Composable fun ShowAllTrackiesLazyColumn(listOfTrackies: List<TrackieViewState>) {

    LazyColumn(

        modifier = Modifier
            .fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,

        content = {

            items(listOfTrackies) {

                Trackie(

                    name = it.name,
                    totalDose = it.totalDose,
                    measuringUnit = it.measuringUnit,
                    repeatOn = it.repeatOn,
                    ingestionTime = it.ingestionTime
                )

                Spacer5()
            }
        }
    )
}