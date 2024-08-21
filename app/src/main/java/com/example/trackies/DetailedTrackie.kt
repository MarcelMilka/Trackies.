package com.example.trackies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardReturn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.buttons.IconButtonToNavigateBetweenActivities
import com.example.trackies.customUI.spacers.Spacer40
import com.example.trackies.detailedTrackie.presentation.DetailedTrackieViewState
import com.example.trackies.detailedTrackie.presentation.ui.*
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.ui.theme.*

@Composable
fun DetailedTrackie(

    uiState: DetailedTrackieViewState,
    displayDetailsOf: TrackieViewState?,
    onReturn: () -> Unit,
    onDelete: () -> Unit
) {

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor),

        content = {

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .background(color = BackgroundColor),

                verticalArrangement = Arrangement.SpaceBetween,

                content = {

//                  Name of trackie, scheduled days, daily dose
                    Column(

                        modifier = Modifier
                            .fillMaxWidth(),

                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,

                        content = {

                            IconButtonToNavigateBetweenActivities(icon = Icons.Rounded.KeyboardReturn) { onReturn() }

                            Spacer40()

                            when (uiState) {

                                DetailedTrackieViewState.Loading -> {

                                    UpperPartOfUiLoading()
                                }

                                is DetailedTrackieViewState.SucceededToLoadData -> {

                                    UpperPartOfUiLoadedSuccessfully(

                                        nameOfTheTrackie = displayDetailsOf!!.name,
                                        repeatOn = displayDetailsOf.repeatOn,
                                        totalDose = displayDetailsOf.totalDose,
                                        measuringUnit = displayDetailsOf.measuringUnit,
                                        onDelete = { onDelete() }
                                    )

                                }

                                DetailedTrackieViewState.FailedToLoadData -> {

                                    UiFailedToLoadData()
                                }
                            }
                        }
                    )

//                  Chart
                    Column(

                        modifier = Modifier
                            .fillMaxWidth(),

                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,

                        content = {

                            when (uiState) {

                                DetailedTrackieViewState.Loading -> {

                                    LowerPartOfUiLoading()
                                }

                                is DetailedTrackieViewState.SucceededToLoadData -> {

                                    LowerPartOfUiLoadedSuccessfully(weeklyRegularity = uiState.trackiesWithWeeklyRegularity[displayDetailsOf!!.name])
                                }

                                DetailedTrackieViewState.FailedToLoadData -> {}
                            }
                        }
                    )
                }
            )
        }
    )
}