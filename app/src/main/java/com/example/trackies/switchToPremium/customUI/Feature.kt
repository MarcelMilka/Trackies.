package com.example.trackies.switchToPremium.customUI

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.texts.TextMedium

@Composable
fun Feature(
    feature: String,
    freeVersion: @Composable () -> Unit,
    premiumVersion: @Composable () -> Unit
) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        content = {

            Row(

                modifier = Modifier
                    .weight(5f, true)
                    .fillMaxHeight(),

                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,

                content = { TextMedium(content = feature) }
            )

            Row(

                modifier = Modifier
                    .weight(2f, true)
                    .fillMaxHeight(),

                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,

                content = { freeVersion() }
            )

            Row(
                modifier = Modifier
                    .width(60.dp)
                    .fillMaxHeight(),

                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,

                content = { premiumVersion() }
            )
        }
    )
}