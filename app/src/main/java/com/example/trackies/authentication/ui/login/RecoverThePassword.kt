package com.example.trackies.authentication.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import com.example.trackies.customUI.buttons.BigDynamicButton
import com.example.trackies.customUI.spacers.Spacer120
import com.example.trackies.customUI.spacers.Spacer200
import com.example.trackies.customUI.spacers.Spacer25
import com.example.trackies.customUI.textFields.EmailInputTextField
import com.example.trackies.customUI.texts.Detail
import com.example.trackies.customUI.texts.BigHeader
import com.example.trackies.ui.theme.BackgroundColor

@Composable
fun RecoverThePassword( onContinue: (String) -> Unit ) {

    //  registration credentials
    var email by remember { mutableStateOf("") }

    var isEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(email) {
        if (email.length >= 3) {
            isEnabled = true
        }

        else {
            isEnabled = false
        }
    }

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

            BigHeader( "Password recovery" )

            Spacer25()

            Column(
                modifier = Modifier
                    .fillMaxWidth(),

                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,

                content = {
                    Detail(content = "Type the email address you used to sign in to the app.")
                    Detail(content = "We will send you instructions how to recover the password.")
                }
            )

            Spacer120()

            EmailInputTextField(
                insertedValue = { email = it },
                assignedFocusRequester = FocusRequester(),
                isActive = {},
                adjustHeightOfUpperSpacer = {},
                onDone = {}
            )

            Spacer25()

            BigDynamicButton( textToDisplay = "Continue.", isEnabled = isEnabled ) { onContinue(email) }

        }
    )
}