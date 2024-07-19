package com.example.trackies

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardReturn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trackies.customUI.buttons.IconButtonToNavigateBetweenActivities
import com.example.trackies.customUI.spacers.Spacer40
import com.example.trackies.customUI.texts.MediumHeader
import com.example.trackies.homeScreen.presentation.SharedViewModel
import com.example.trackies.ui.theme.BackgroundColor

@Composable
fun ParticularTrackie(
    viewModel: SharedViewModel = viewModel(),
    onReturn: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()
    
    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor),

        content = {

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),

                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,

                content = {

                    IconButtonToNavigateBetweenActivities(icon = Icons.Rounded.KeyboardReturn) { onReturn() }

                    Spacer40()

                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .border(2.dp, White)
                            .height(30.dp),

                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,

                        content = {

                            MediumHeader(content = "name of a trackie")
                        }
                    )
                }
            )
        }
    )
}