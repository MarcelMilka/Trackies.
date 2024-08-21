package com.example.trackies.sharedComponentsOfUi.trackie

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.buttons.IconButtonDetails
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.ui.theme.SecondaryColor

@Composable fun Trackie(
    name: String,
    totalDose: Int,
    measuringUnit: String,
    repeatOn: List<String>,
    ingestionTime: Map<String, Int>?,

    stateOfTheTrackie: Boolean,

    onCheck: () -> Unit,
    onDisplayDetails: () -> Unit
) {

    var targetValueOfProgressBar by remember { mutableIntStateOf(0) }
    var targetValueOfCurrentDose by remember { mutableIntStateOf(0) }

    when (stateOfTheTrackie) {

        true -> {

            targetValueOfProgressBar = 100
            targetValueOfCurrentDose = totalDose
        }
        false -> {

            targetValueOfProgressBar = 0
            targetValueOfCurrentDose = 0
        }
    }

    val progressOfTheProgressBar by animateIntAsState(
        targetValue = targetValueOfProgressBar,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 50,
            easing = LinearOutSlowInEasing
        ),
        label = "",
    )

    val progressOfTheCurrentDose by animateIntAsState(
        targetValue = targetValueOfCurrentDose,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 50,
            easing = LinearOutSlowInEasing
        ),
        label = "",
    )


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

                            TrackieProgressBar(progress = progressOfTheProgressBar)
                            TextSmall( content = " $progressOfTheCurrentDose/$totalDose $measuringUnit" )
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

                content = { IconButtonDetails { onDisplayDetails() } }
            )

            when (stateOfTheTrackie) {

                true -> {
                    MagicButtonMarkedAsIngested()
                    Spacer(Modifier.width(5.dp))
                }

                false -> {

                    MagicButton(
                        ingestionTime = ingestionTime,
                        totalDose = totalDose,
                        measuringUnit = measuringUnit,

                        onCheck = { onCheck() }
                    )
                    Spacer(Modifier.width(5.dp))
                }
            }
        }
    )
}