package com.example.trackies.homeScreen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trackies.customUI.buttons.ButtonAddAnotherTrackie
import com.example.trackies.customUI.buttons.ButtonShowAllTrackies
import com.example.trackies.customUI.buttons.IconButtonToNavigateBetweenActivities
import com.example.trackies.customUI.lazyColumns.HomeScreenLazyColumn
import com.example.trackies.customUI.spacers.Spacer40
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.MediumHeader
import com.example.trackies.ui.theme.BackgroundColor

@Composable
fun HomeScreen(
    viewModel: SharedViewModel = viewModel(),
    onAddNewTrackie: () -> Unit,
    onSignOut: () -> Unit,
    onDelete: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

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

                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,

                content = {

                    IconButtonToNavigateBetweenActivities(icon = Icons.Rounded.Person) {}

                    Spacer40()

                    MediumHeader(content = "Your today's trackies")

                    Spacer5()

                    HomeScreenLazyColumn(
                        uiState = uiState,
                        onAddNewTrackie = { onAddNewTrackie() }
                    )

                    Spacer5()

                    ButtonShowAllTrackies {  }

                    Spacer5()

                    ButtonAddAnotherTrackie { onAddNewTrackie() }

                }
            )
        }
    )
}