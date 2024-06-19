package com.example.trackies.homeScreen.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trackies.customUI.buttons.BigStaticPrimaryButton
import com.example.trackies.customUI.spacers.Spacer120
import com.example.trackies.customUI.texts.Header
import com.example.trackies.customUI.texts.TextSmall
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
        verticalArrangement = Arrangement.Top,

        content = {


            Header( "home screen" )

            TextSmall(uniqueIdentifier)
            TextSmall("${viewModel.uiState.collectAsState().value}")

            BigStaticPrimaryButton("sign out") { onSignOut() }

            Spacer120()

            BigStaticPrimaryButton("delete the account") { onDelete() }
        }
    )
}