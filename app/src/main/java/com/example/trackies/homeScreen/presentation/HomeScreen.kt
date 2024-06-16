package com.example.trackies.homeScreen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.trackies.authentication.repository.FirebaseAuthentication
import com.example.trackies.customUI.buttons.BigStaticPrimaryButton
import com.example.trackies.customUI.spacers.Spacer120
import com.example.trackies.customUI.texts.Header
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.ui.theme.BackgroundColor

@Composable
fun HomeScreen(
    uniqueIdentifier: String,
    onSignOut: () -> Unit,
    onDelete: () -> Unit
) {

    val firebase =  FirebaseAuthentication()

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

            BigStaticPrimaryButton("sign out") { onSignOut() }

            Spacer120()

            BigStaticPrimaryButton("delete the account") { onDelete() }

        }
    )
}