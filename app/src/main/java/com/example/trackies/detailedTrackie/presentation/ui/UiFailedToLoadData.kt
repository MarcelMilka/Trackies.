package com.example.trackies.detailedTrackie.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.trackies.customUI.spacers.Spacer200
import com.example.trackies.customUI.spacers.Spacer25
import com.example.trackies.customUI.texts.BigHeader
import com.example.trackies.customUI.texts.Detail

@Composable
fun UiFailedToLoadData() {

    Column(

        modifier = Modifier
            .fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,

        content = {

            Spacer200()
            BigHeader(content = "Oops...")
            Spacer25()
            Detail(content = "an error occurred while loading data.")
        }
    )
}