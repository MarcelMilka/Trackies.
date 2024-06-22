package com.example.trackies.customUI.trackie

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Details
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.buttons.IconButtonDetails
import com.example.trackies.customUI.buttons.MagicButton
import com.example.trackies.customUI.progressIndicators.ProgressBar
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.ui.theme.SecondaryColor

@Composable fun Trackie(
    name: String,
    totalDose: Int,
    measuringUnit: String,
    repeatOn: List<String>,
    ingestionTime: Map<String, Int>?
) {

    Row(

        modifier = Modifier

            .fillMaxWidth()
            .height(60.dp)
            .background(
                color = SecondaryColor,
                shape = RoundedCornerShape(20.dp)
            ),

        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,

        content = {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
//                    .border(2.dp, White)
                    .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                    .weight(2f, true),

                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,

                content = {

                    TextMedium( name )

                    Spacer5()

                    Row(
                        modifier = Modifier
                            .height(12.dp)
                            .fillMaxWidth(),

                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Bottom,

                        content = {

                            ProgressBar(currentValue = 0, goal = totalDose)
                            TextSmall( content = " 0/$totalDose $measuringUnit" )
                        }
                    )
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(50.dp),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

                content = {
                    IconButtonDetails {  }
                }
            )

            MagicButton(
                ingestionTime = ingestionTime,
                totalDose = totalDose,
                measuringUnit = measuringUnit
            ) {}
        }
    )
}