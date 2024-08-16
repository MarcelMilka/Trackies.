package com.example.trackies.homeScreen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.buttons.ButtonAddAnotherTrackie
import com.example.trackies.customUI.buttons.ButtonShowAllTrackies
import com.example.trackies.customUI.buttons.IconButtonToNavigateBetweenActivities
import com.example.trackies.customUI.homeScreen.GraphToDisplay
import com.example.trackies.customUI.homeScreen.HomeScreenGraph
import com.example.trackies.customUI.homeScreen.RowWithRadioButtons
import com.example.trackies.customUI.lazyColumns.HomeScreenLazyColumn
import com.example.trackies.customUI.spacers.Spacer40
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.MediumHeader
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.ui.theme.BackgroundColor

@Composable
fun HomeScreen(

    uiState: SharedViewModelViewState,
    graphToDisplay: GraphToDisplay,
    onOpenSettings: () -> Unit,
    onAddNewTrackie: () -> Unit,
    onCheck: (trackieViewState: TrackieViewState) -> Unit,
    onShowAllTrackies: () -> Unit,
    onSignOut: () -> Unit,
    onDelete: () -> Unit,
    onChangeGraph: (GraphToDisplay) -> Unit,
    onDisplayDetails: (trackieViewState: TrackieViewState) -> Unit
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

                            MediumHeader(content = "Your today's trackies")

                            Spacer5()

                            HomeScreenLazyColumn(
                                uiState = uiState,
                                onAddNewTrackie = { onAddNewTrackie() },

                                onCheck = { onCheck(it) },
                                onDisplayDetails = { onDisplayDetails(it) }
                            )

                            Spacer5()

                            ButtonShowAllTrackies { onShowAllTrackies() }

                            Spacer5()

                            ButtonAddAnotherTrackie { onAddNewTrackie() }

                            Spacer40()
                        }
                    )

                    Column(

                        modifier = Modifier
                            .fillMaxWidth(),

                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Bottom,

                        content = {

                            MediumHeader(content = "Regularity")

                            Spacer5()

                            RowWithRadioButtons(graphToDisplay = graphToDisplay) { onChangeGraph(it) }

                            Spacer5()

                            HomeScreenGraph(uiState = uiState)
                        }
                    )
                }
            )
        }
    )
}