package com.example.trackies.homeScreen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trackies.customUI.buttons.IconButtonToNavigateBetweenActivities
import com.example.trackies.customUI.lazyColumns.HomeScreenLazyColumn
import com.example.trackies.customUI.spacers.Spacer40
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.Detail
import com.example.trackies.customUI.texts.MediumHeader
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.ui.theme.BackgroundColor

@Composable
fun HomeScreen(
    uniqueIdentifier: String,
    viewModel: HomeScreenViewModel = viewModel(),
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

            HomeScreenLazyColumn( uiState = uiState )
        }
    )
}