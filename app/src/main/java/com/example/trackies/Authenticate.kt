package com.example.trackies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.trackies.customUI.buttons.BigStaticPrimaryButton
import com.example.trackies.customUI.spacers.Spacer120
import com.example.trackies.customUI.spacers.Spacer200
import com.example.trackies.customUI.spacers.Spacer25
import com.example.trackies.customUI.texts.Detail
import com.example.trackies.customUI.texts.Header
import com.example.trackies.ui.theme.BackgroundColor

@Composable
fun Authenticate( navigate: () -> Unit ) {

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
            Header(content = "Check your e-mail")
            Spacer25()
            Column(
                modifier = Modifier
                    .fillMaxWidth(),

                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,

                content = {
                    Detail(content = "We have sent you an email with a verification link.")
                    Detail(content = "Click the link to confirm your email address")
                    Detail(content = "and return to the app to sign in.")
                }
            )
            Spacer200()
            BigStaticPrimaryButton( textToDisplay = "Okay!", onClick = { navigate() } )
        }
    )
}