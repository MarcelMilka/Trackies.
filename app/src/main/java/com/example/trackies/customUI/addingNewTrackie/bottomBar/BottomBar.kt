package com.example.trackies.customUI.addingNewTrackie.bottomBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.buttons.MediumTextButton
import com.example.trackies.ui.theme.SecondaryColor

@Composable
fun AddNewTrackieBottomBar(

    buttonAddIsEnabled: Boolean,

    onClearAll: () -> Unit,
    onAdd: () -> Unit
) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(
                color = SecondaryColor,
                shape = RoundedCornerShape(20.dp)
            ),

        content = {

            Row(

                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,

                content = {

                    MediumTextButton( text = "clear all" ) { onClearAll() }

                    BottomBarDynamicButton( buttonAddIsEnabled = buttonAddIsEnabled ) { onAdd() }
                }
            )
        }
    )
}