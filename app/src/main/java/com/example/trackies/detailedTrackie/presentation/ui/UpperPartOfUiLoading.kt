package com.example.trackies.detailedTrackie.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.spacers.Spacer10
import com.example.trackies.ui.theme.SecondaryColor

@Composable
fun UpperPartOfUiLoading () {

    Column(

        modifier = Modifier
            .fillMaxWidth(),

        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,

        content = {

//          Header which displays name of the trackie.
            Row(
                modifier = Modifier
                    .fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween,

                content = {

//                  Medium header which displays name of the trackie
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .height(30.dp),

                        shape = RoundedCornerShape(20.dp),

                        color = SecondaryColor
                    ) {}

//                  IconButton
                    Surface(
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp),

                        shape = RoundedCornerShape(30.dp),

                        color = SecondaryColor
                    ) {}
                }
            )

            Spacer10()

//          Text "Scheduled days" and days of week, on which a trackie should be ingested
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.45f)
                    .height(35.dp),

                shape = RoundedCornerShape(10.dp),

                color = SecondaryColor
            ) {} //2 (detail)

            Spacer10()

//          Text "Daily dose" and daily dose of the trackie
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.45f)
                    .height(35.dp),

                shape = RoundedCornerShape(10.dp),

                color = SecondaryColor
            ) {}
        }
    )
}