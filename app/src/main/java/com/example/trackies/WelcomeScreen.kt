package com.example.trackies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.trackies.customUI.buttons.BigStaticPrimaryButton
import com.example.trackies.customUI.spacers.Spacer200
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.Header
import com.example.trackies.ui.theme.BackgroundColor

@Composable
fun WelcomeScreen( navigate: (String) -> Unit ) {

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
            Header( "Hey there!" )
            Spacer200()
            BigStaticPrimaryButton( textToDisplay = "Create a new account." ) { navigate("SignUp") }
            Spacer5()
            BigStaticPrimaryButton( textToDisplay = "I have an account." ) { navigate("SignIn") }
        }
    )
}