package com.example.trackies.homeScreen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.buttons.IconButtonToNavigateBetweenActivities
import com.example.trackies.customUI.spacers.Spacer40
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.MediumHeader
import com.example.trackies.enumClasses.HomeScreenGraphToDisplay
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.homeScreen.presentation.ui.*
import com.example.trackies.ui.theme.BackgroundColor
import com.example.trackies.ui.theme.SecondaryColor
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(
    heightOfHomeScreenLazyColumn: StateFlow<Int>,
    uiState: SharedViewModelViewState,
    typeOfHomeScreenGraphToDisplay: HomeScreenGraphToDisplay,
    onOpenSettings: () -> Unit,
    onAddNewTrackie: () -> Unit,
    onMarkTrackieAsIngestedForToday: (trackieViewState: TrackieViewState) -> Unit,
    onShowAllTrackies: () -> Unit,
    onChangeGraph: (HomeScreenGraphToDisplay) -> Unit,
    onDisplayDetailedTrackie: (trackieViewState: TrackieViewState) -> Unit
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

                    Column(

                        modifier = Modifier
                            .fillMaxWidth(),

                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,

                        content = {

                            IconButtonToNavigateBetweenActivities(icon = Icons.Rounded.Person) { onOpenSettings() }

                            Spacer40()

                            when (uiState) {

                                SharedViewModelViewState.Loading -> {

                                    Box(

                                        modifier = Modifier
                                            .fillMaxWidth(0.80f)
                                            .height(30.dp)
                                            .background(

                                                color = SecondaryColor,
                                                shape = RoundedCornerShape(20.dp)
                                            )
                                    )

                                    Spacer5()

                                    PreviewOfListOfTrackiesLoading()

                                    Spacer5()

                                    LoadingButtonShowAllTrackies()

                                    Spacer5()

                                    LoadingButtonAddAnotherTrackie()
                                }

                                is SharedViewModelViewState.LoadedSuccessfully -> {

                                    MediumHeader(content = "Your today's trackies")

                                    Spacer5()

                                    PreviewOfListOfTrackiesLoadedSuccessfully(
                                        heightOfHomeScreenLazyColumn = heightOfHomeScreenLazyColumn,
                                        uiState = uiState,

                                        onCheck = { onMarkTrackieAsIngestedForToday(it) },
                                        onDisplayDetails = { onDisplayDetailedTrackie(it) }
                                    )

                                    Spacer5()

                                    ButtonShowAllTrackies { onShowAllTrackies() }

                                    Spacer5()

                                    ButtonAddAnotherTrackie { onAddNewTrackie() }
                                }

                                SharedViewModelViewState.FailedToLoadData -> {}
                            }

                            Spacer40()
                        }
                    )

                    Column(

                        modifier = Modifier
                            .fillMaxWidth(),

                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Bottom,

                        content = {

                            when (uiState) {

                                SharedViewModelViewState.Loading -> {

                                    Box(

                                        modifier = Modifier
                                            .fillMaxWidth(0.80f)
                                            .height(30.dp)
                                            .background(

                                                color = SecondaryColor,
                                                shape = RoundedCornerShape(20.dp)
                                            )
                                    )

                                    Spacer5()

                                    RowWithRadioButtonsLoading()

                                    Spacer5()

                                    RegularityChartLoading()
                                }

                                is SharedViewModelViewState.LoadedSuccessfully -> {

                                    MediumHeader(content = "Regularity")

                                    Spacer5()

                                    RowWithRadioButtonsLoadedSuccessfully(graphToDisplay = typeOfHomeScreenGraphToDisplay) { onChangeGraph(it) }

                                    Spacer5()

                                    RegularityChartLoadedSuccessFully(uiState = uiState)
                                }

                                SharedViewModelViewState.FailedToLoadData -> {}
                            }
                        }
                    )
                }
            )
        }
    )
}