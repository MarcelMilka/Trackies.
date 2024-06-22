package com.example.trackies.homeScreen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trackies.ui.theme.BackgroundColor

@Composable
fun HomeScreen(
    uniqueIdentifier: String,
    viewModel: HomeScreenViewModel = viewModel(),
    onSignOut: () -> Unit,
    onDelete: () -> Unit,
) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(
                color = BackgroundColor
            ),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        content = {}
    )
}