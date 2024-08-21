package com.example.trackies.sharedComponentsOfUi.trackie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trackies.ui.theme.CheckedTrackie

@Preview
@Composable
fun MagicButtonMarkedAsIngested() {

    Row(

        modifier = Modifier
            .width(70.dp)
            .height(50.dp)
            .background(
                color = CheckedTrackie,
                shape = RoundedCornerShape(18.dp)
            ),

        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,

        content = {

            Icon(

                imageVector = Icons.Rounded.EmojiEvents,
                tint = Color.White,
                contentDescription = null
            )
        }
    )
}