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
import com.example.trackies.customUI.buttons.IconButtonDelete
import com.example.trackies.customUI.buttons.IconButtonDetails
import com.example.trackies.customUI.buttons.IconButtonToNavigateBetweenActivities
import com.example.trackies.customUI.spacers.Spacer40
import com.example.trackies.customUI.texts.MediumHeader
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.homeScreen.presentation.SharedViewModel
import com.example.trackies.ui.theme.BackgroundColor
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DetailedTrackie(
    trackieToDisplay: TrackieViewState?,
    onReturn: () -> Unit,
    onDelete: () -> Unit
) {

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
                            .height(30.dp),

                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,

                        content = {

                            MediumHeader(content = if (trackieToDisplay != null) { trackieToDisplay.name } else {"An error occurred."})

                            IconButtonDelete { onDelete() }
                        }
                    )
                }
            )
        }
    )
}