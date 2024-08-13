package com.example.trackies.confirmDeleting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AllInclusive
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.Detail
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.switchToPremium.customUI.Feature
import com.example.trackies.switchToPremium.customUI.Premium
import com.example.trackies.ui.theme.BackgroundColor
import com.example.trackies.ui.theme.PrimaryColor
import com.example.trackies.ui.theme.White50
import com.example.trackies.ui.theme.quickSandBold

@Composable
fun ConfirmDeleting(

    onConfirm: () -> Unit,
    onDecline: () -> Unit
) {

    Column(

        modifier = Modifier.fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        content = {

            Column(

                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(0.2f)
                    .background(BackgroundColor, RoundedCornerShape(20.dp)),

                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,

                content = {

                    Button(

                        content = { TextMedium("Confirm") },
                        onClick = { onConfirm() }
                    )

                    Button(

                        content = { TextMedium("Decline") },
                        onClick = { onDecline() }
                    )
                }
            )
        }
    )
}
