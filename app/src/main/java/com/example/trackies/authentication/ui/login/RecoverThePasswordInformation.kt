package com.example.trackies.authentication.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.trackies.customUI.buttons.BigStaticPrimaryButton
import com.example.trackies.customUI.spacers.Spacer200
import com.example.trackies.customUI.spacers.Spacer25
import com.example.trackies.customUI.texts.Detail
import com.example.trackies.customUI.texts.BigHeader
import com.example.trackies.ui.theme.BackgroundColor

@Composable
fun RecoverThePasswordInformation( navigate: (String) -> Unit ) {


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
            BigHeader(content = "Check your e-mail")
            Spacer25()
            Column(
                modifier = Modifier
                    .fillMaxWidth(),

                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,

                content = {
                    Detail(content = "We have just sent to you an instruction")
                    Detail(content = "to recover your password.")
                }
            )
            Spacer200()
            BigStaticPrimaryButton( textToDisplay = "Okay!", onClick = { navigate( "SignIn" ) } )

        }
    )
}