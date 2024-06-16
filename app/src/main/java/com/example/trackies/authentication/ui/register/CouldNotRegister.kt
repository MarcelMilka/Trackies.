package com.example.trackies.authentication.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.trackies.customUI.buttons.BigStaticPrimaryButton
import com.example.trackies.customUI.spacers.Spacer200
import com.example.trackies.customUI.spacers.Spacer25
import com.example.trackies.customUI.texts.Detail
import com.example.trackies.customUI.texts.Header
import com.example.trackies.ui.theme.BackgroundColor

@Composable
fun CouldNotRegister(
    errorCause: String,
    navigate: () -> Unit
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

            Spacer200()
            Header(content = "An error occurred.")
            Spacer25()
            Column(
                modifier = Modifier
                    .fillMaxWidth(),

                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,

                content = {
                    Detail(content = "Sorry, already it's not possible to sign you up, because")
                    Detail(content = errorCause)
                }
            )
            Spacer200()
            BigStaticPrimaryButton( textToDisplay = "Okay!", onClick = { navigate() } )
        }
    )
}