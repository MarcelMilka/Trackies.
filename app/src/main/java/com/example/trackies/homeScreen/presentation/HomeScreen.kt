package com.example.trackies.homeScreen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trackies.customUI.buttons.IconButtonToNavigateBetweenActivities
import com.example.trackies.customUI.lazyColumns.HomeScreenLazyColumn
import com.example.trackies.customUI.spacers.Spacer40
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.MediumHeader
import com.example.trackies.homeScreen.buisness.LicenseViewState
import com.example.trackies.ui.theme.BackgroundColor

@Composable
fun HomeScreen(
    viewModel: SharedViewModel = viewModel(),
    onAddNewTrackie: (LicenseViewState) -> Unit,
    onSignOut: () -> Unit,
    onDelete: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(
                color = BackgroundColor
            ),

        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,

        content = {

            IconButtonToNavigateBetweenActivities(icon = Icons.Rounded.Person) {}

            Spacer40()

            MediumHeader(content = "Your today's trackies")

            Spacer5()

            HomeScreenLazyColumn(
                uiState = uiState,
                passLicenseInformation = {
                    onAddNewTrackie(it) }
            )
        }
    )
}