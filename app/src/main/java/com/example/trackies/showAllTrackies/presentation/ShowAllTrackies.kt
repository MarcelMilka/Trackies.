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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trackies.customUI.buttons.IconButtonToNavigateBetweenActivities
import com.example.trackies.customUI.buttons.MediumRadioTextButton
import com.example.trackies.customUI.spacers.Spacer40
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.MediumHeader
import com.example.trackies.customUI.trackie.Trackie
import com.example.trackies.homeScreen.presentation.HomeScreenViewState
import com.example.trackies.homeScreen.presentation.SharedViewModel
import com.example.trackies.ui.theme.BackgroundColor

@Composable
fun ShowAllTrackies(
    viewModel: SharedViewModel = viewModel(),
    onReturn: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    var wholeWeek by remember { mutableStateOf(false) }
    var today by remember { mutableStateOf(true) }

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
                            }

                            MediumRadioTextButton(text = "today", isSelected = today) {

                                today = it
                                wholeWeek = !it
                            }
                        }
                    )

                    Spacer5()

                    LazyColumn(

                        modifier = Modifier
                            .fillMaxSize(),

                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,

                        content = {

                            when (uiState) {

                                HomeScreenViewState.Loading -> {}

                                is HomeScreenViewState.LoadedSuccessfully -> {

                                    items(((uiState as HomeScreenViewState.LoadedSuccessfully).trackiesForToday)) { trackieViewState ->

                                        Trackie(
                                            name = trackieViewState.name,
                                            totalDose = trackieViewState.totalDose,
                                            measuringUnit = trackieViewState.measuringUnit,
                                            repeatOn = trackieViewState.repeatOn,
                                            ingestionTime = trackieViewState.ingestionTime
                                        )

                                        Spacer5()
                                    }
                                }

                                HomeScreenViewState.FailedToLoadData -> {}
                            }
                        }
                    )
                }
            )
        }
    )
}