package com.example.trackies.customUI.addingNewTrackie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trackies.ui.theme.SecondaryColor

@Composable
fun AddTimeOfIngestion() {

    Row(

        modifier = Modifier

            .fillMaxWidth()
            .height(40.dp)
            .background(
                color = SecondaryColor,
                shape = RoundedCornerShape(20.dp)
            ),

        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,

        content = {

            IconButton(

                onClick = {},
                content = {

                    Icon(

                        imageVector = Icons.Rounded.Add,
                        tint = Color.White,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
            )
        }
    )
}