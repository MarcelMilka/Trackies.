package com.example.trackies.customUI.addingNewTrackie

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.customUI.texts.TextMedium50
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.ui.theme.MyFonts
import com.example.trackies.ui.theme.SecondaryColor
import com.example.trackies.ui.theme.White50

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun Reference() {

//  adjust height of the elements
        var areExpanded by remember { mutableStateOf(false) }

//      Height of the column
        var targetHeightOfTheColumn by remember { mutableStateOf(50) }
        var heightOfTheColumn = animateIntAsState(
            targetValue = targetHeightOfTheColumn,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 50,
                easing = LinearOutSlowInEasing
            ),
            label = "adjust height of the column which displays the surface and the supporting text",
        )

//      Height of the surface
        var targetHeightOfTheSurface by remember { mutableStateOf(50) }
        var heightOfTheSurface = animateIntAsState(
            targetValue = targetHeightOfTheColumn,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 50,
                easing = LinearOutSlowInEasing
            ),
            label = "adjust height of the surface which displays the content",
        )


    Column( // holder of surface and supporting text

        modifier = Modifier
            .fillMaxWidth()
            .height(heightOfTheColumn.value.dp)
            .border(2.dp, White),

        content = {

            Surface( // background of the composable, adjusts height of the whole composable and displays appropriate data

                modifier = Modifier
                    .fillMaxWidth()
                    .height(heightOfTheSurface.value.dp),

                color = SecondaryColor,
                shape = RoundedCornerShape(20.dp),

                onClick = {

                    when (areExpanded) {

                        true -> { // collapse

                            targetHeightOfTheColumn = 50
                            targetHeightOfTheSurface = 50

                            areExpanded = !areExpanded
                        }
                        false -> { // expand

                            targetHeightOfTheColumn = 100
                            targetHeightOfTheSurface = 100

                            areExpanded = !areExpanded
                        }
                    }
                },

                content = {

                    Column( // sets padding

                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {

                        Column( // displays what takes, may also display the premium logo

                            modifier = Modifier
                                .fillMaxWidth()
//                            .border(2.dp, White)
                                .height(30.dp),

                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceBetween,

                            content = {
                                TextMedium50("miłość, kutas, kokakola!")
                                TextSmall("click to insert ...")
                            } // TODO: insert custom name
                        )

                        when (areExpanded) {

                            true -> {

                                Column( // displays a

                                    modifier = Modifier
                                        .fillMaxWidth()
//                                      .border(2.dp, White)
                                        .height(30.dp),

                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.SpaceBetween,

                                    content = {
                                        TextMedium50("miłość, kutas, kokakola!")
                                        TextSmall("click to insert ...")
                                    } // TODO: insert custom name
                                )
                            }

                            false -> { // display inserted text, if it's inserted

                                Column( // displays a

                                    modifier = Modifier
                                        .fillMaxWidth()
//                                      .border(2.dp, White)
                                        .height(30.dp),

                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.SpaceBetween,

                                    content = {
                                        TextMedium("$")
                                    } // TODO: insert custom name
                                )
                            }
                        }

                        // TODO: add required elements accordingly to the intention of the UI
                    }
                }
            )

            Row( // place for the supporting text

                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .padding(start = 10.dp, end = 10.dp)
                    .border(2.dp, Red),

                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,

                content = {

                    // TODO: implement if/when statements which are going to show this section
                    val descriptionOfTheError = ErrorMessages.ExemplaryError().description

                    AnimatedVisibility(

                        visible = false, // TODO: implement a variable which is going to determine whether the error is visible, or not
                        enter = fadeIn(animationSpec = tween(500)),
                        exit = fadeOut(animationSpec = tween(500)),

                        content = { TextSmall( content = descriptionOfTheError ) }
                    )
                }
            )
        }
    )
}

private sealed class ErrorMessages {

    data class ExemplaryError(val description: String = "description of the error")
}