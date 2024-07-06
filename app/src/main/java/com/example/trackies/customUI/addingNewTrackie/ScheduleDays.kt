package com.example.trackies.customUI.addingNewTrackie

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.buttons.MediumRadioTextButton
import com.example.trackies.ui.theme.MyFonts
import com.example.trackies.ui.theme.SecondaryColor
import com.example.trackies.ui.theme.White50

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleDays(
    actualDays: List<String>,
    onApplyChosenDays: (List<String>) -> Unit
) {

    var insertedNameIsVisible by remember { mutableStateOf(false) }
    var textFieldIsVisible by remember { mutableStateOf(false) }

//  width of the divider
    var dividerTargetValue by remember { mutableFloatStateOf(0f) }


//  height of the column and the surface
    var areExpanded by remember { mutableStateOf(value = true) } // true by default, because launched effect on the first launch inverts value

//  height of the column
    var columnTargetValue by remember { mutableIntStateOf(50) }
    var columnAdditionalValue by remember { mutableIntStateOf(0) }

//  height of the surface
    var surfaceTargetValue by remember { mutableIntStateOf(50) }

//  chosen days of week
    val daysOfWeek = listOf(
        "Mon" to "monday",
        "Tue" to "tuesday",
        "Wed" to "wednesday",
        "Thu" to "thursday",
        "Fri" to "friday",
        "Sat" to "saturday",
        "Sun" to "sunday"
    )

    var repeatOn = remember { mutableListOf<String>() }
    val selectionStates = remember { mutableStateMapOf<String, Boolean>().apply {
        daysOfWeek.forEach { (_, day) -> this[day] = false }
        }
    }

    LaunchedEffect(actualDays) {

        repeatOn = actualDays.toMutableList()

        if (repeatOn.isEmpty()) {

            columnTargetValue = 50
            surfaceTargetValue = 50

            daysOfWeek.forEach { (shortName, fullName) ->

                selectionStates[fullName] = false
            }

        }
    }

//  adjust height of the column and surface accordingly to the value of "areExpanded" and "nameIsUnique"
    LaunchedEffect(areExpanded, repeatOn) {

        when (areExpanded) {

            true -> {

                if (repeatOn.isNotEmpty()) {

                    insertedNameIsVisible = true
                    textFieldIsVisible = false

                    columnTargetValue = 85
                    surfaceTargetValue = 85
                }

                else {

                    dividerTargetValue = 0f
                    columnTargetValue = 50
                    surfaceTargetValue = 50
                }
            }

            false -> {

                textFieldIsVisible = true

                dividerTargetValue = 1f
                columnTargetValue = 106
                surfaceTargetValue = 106

            }
        }

        if (repeatOn.isEmpty()) { columnAdditionalValue = 20 }
        else { columnAdditionalValue = 0 }

    }

//  animators
    val widthOfDivider = animateFloatAsState(

        targetValue = dividerTargetValue,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 50,
            easing = LinearOutSlowInEasing
        ),
        label = "adjusting height of the column which displays data"
    ).value

    val heightOfColumn = animateIntAsState(

        targetValue = (columnTargetValue + columnAdditionalValue),
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 50,
            easing = LinearOutSlowInEasing
        ),
        label = "adjusting height of the column which displays data"
    ).value

    val heightOfSurface = animateIntAsState(

        targetValue = surfaceTargetValue,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 50,
            easing = LinearOutSlowInEasing
        ),
        label = "adjusting height of the surface which displays data"
    ).value

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .height(heightOfColumn.dp),

        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,

        content = {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(heightOfSurface.dp),

                color = SecondaryColor,
                shape = RoundedCornerShape(20.dp),

                onClick = {

                    areExpanded = !areExpanded

                    if (areExpanded) { onApplyChosenDays(repeatOn) }
                },

                content = {

                    Column(

                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),

                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,

                        content = {

//                          Text "Daily dosage" and divider
                            Column(

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),

                                verticalArrangement = Arrangement.Center,

                                content = {

                                    Text( text = "Schedule days",
                                        style = MyFonts.titleMedium,
                                        color = White50,

                                        modifier = Modifier.padding(start = 15.dp)
                                    )

                                    Divider(

                                        color = White50,

                                        modifier = Modifier
                                            .fillMaxWidth(widthOfDivider)
                                            .padding(start = 15.dp, end = 15.dp)
                                    )
                                }
                            )

//                          Text and custom radio buttons
                            Column(

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),

                                verticalArrangement = Arrangement.Top,

                                content = {

//                                  Chosen days of week
                                    AnimatedVisibility(

                                        visible = areExpanded,
                                        enter = fadeIn(animationSpec = tween(2000)),
                                        exit = fadeOut(animationSpec = tween(100))
                                    ) {

                                        Text(
                                            text = "${repeatOn.joinToString(", ") { it.substring(0, 3).capitalize() }}",
                                            style = MyFonts.titleMedium,
                                            modifier = Modifier
                                                .padding(start = 15.dp)
                                        )
                                    }

//                                  Radio buttons and supporting text
                                    AnimatedVisibility(

                                        visible = !areExpanded,
                                        enter = fadeIn(animationSpec = tween(2000)),
                                        exit = fadeOut(animationSpec = tween(100))
                                    ) {

                                        Text(
                                            text = "Select which days you want to ingest the substance",
                                            style = MyFonts.titleSmall,
                                            modifier = Modifier
                                                .padding(start = 15.dp)
                                        )

                                        Row(

                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(start = 15.dp),

                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically,

                                            content = {

                                                daysOfWeek.forEach { (shortName, fullName) ->

                                                    MediumRadioTextButton(
                                                        text = shortName,
                                                        isSelected = selectionStates[fullName] ?: false
                                                    ) {

                                                        selectionStates[fullName] = !selectionStates[fullName]!!

                                                        if (selectionStates[fullName] == true) { repeatOn.add(fullName) }
                                                        else { repeatOn.remove(fullName) }
                                                        repeatOn.sortBy { day -> daysOfWeek.indexOfFirst { it.second == day } }
                                                    }
                                                }
                                            }
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            )


//          Display supporting text which indicates an error occurred
            AnimatedVisibility(

                visible = (repeatOn.isEmpty()),
                enter = fadeIn(animationSpec = tween(500)),
                exit = fadeOut(animationSpec = tween(500))
            ) { Text(text = "At least one day of week must be chosen",
                style = MyFonts.titleSmall,
                modifier = Modifier.padding(start = 10.dp)
            ) }
        }
    )
}