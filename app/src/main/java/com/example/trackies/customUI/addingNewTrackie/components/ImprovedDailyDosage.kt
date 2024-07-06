package com.example.trackies.customUI.addingNewTrackie.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import com.example.trackies.customUI.addingNewTrackie.viewModel.AddNewTrackieViewModel
import com.example.trackies.customUI.addingNewTrackie.viewModel.IsActive
import com.example.trackies.customUI.texts.*
import com.example.trackies.ui.theme.SecondaryColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun ImprovedDailyDosage(
    viewModel: AddNewTrackieViewModel
) {

    var measuringUnit by remember { mutableStateOf("") }
    var totalDailyDose by remember { mutableStateOf(0) }

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

//  control what should be displayed
    var displayFieldWithInsertedDose by remember { mutableStateOf(false) }
    var displayFieldWithMeasuringUnits by remember { mutableStateOf(false) }
    var displayFieldWithTextField by remember { mutableStateOf(false) }

    var hint by remember { mutableStateOf(DailyDosageHint.InsertDailyDosage().message) }

//  follow changes
    LaunchedEffect(viewModel.activityState.value) {

        viewModel.activityState.collect {

            if (areExpanded && viewModel.activityState.value.dailyDosageIsActive == false) {

                if (measuringUnit == "") {

                    targetHeightOfTheColumn = 50
                    targetHeightOfTheSurface = 50

                    displayFieldWithInsertedDose = false
                    displayFieldWithMeasuringUnits = false
                    displayFieldWithTextField = false

                    hint = DailyDosageHint.InsertDailyDosage().message
                }

                else {

                    targetHeightOfTheColumn = 80
                    targetHeightOfTheSurface = 80

                    displayFieldWithInsertedDose = true
                    displayFieldWithMeasuringUnits = false
                    displayFieldWithTextField = false

                    hint = DailyDosageHint.EditNewName().message
                }

                areExpanded = false
            }
        }
    }

    Column( // holder of surface and supporting text

        modifier = Modifier
            .fillMaxWidth()
            .height(heightOfTheColumn.value.dp),

        content = {

            Surface( // background of the composable, adjusts height of the whole composable and displays appropriate data

                modifier = Modifier
                    .fillMaxWidth()
                    .height(heightOfTheSurface.value.dp),

                color = SecondaryColor,
                shape = RoundedCornerShape(20.dp),

                onClick = {

                    when (areExpanded) {

                     // collapse
                        true -> {

                            viewModel.deActivate(whatToDeactivate = IsActive.DailyDosage)

                            if (measuringUnit == "") {

                                hint = DailyDosageHint.InsertDailyDosage().message

                                targetHeightOfTheColumn = 50
                                targetHeightOfTheSurface = 50

                                displayFieldWithInsertedDose = false
                                displayFieldWithMeasuringUnits = false
                                displayFieldWithTextField = false
                            }

                            else {

                                targetHeightOfTheColumn = 80
                                targetHeightOfTheSurface = 80

                                CoroutineScope(Dispatchers.Default).launch {

                                    delay(250)

                                    hint = DailyDosageHint.EditNewName().message

                                    displayFieldWithInsertedDose = true
                                }

                                displayFieldWithMeasuringUnits = false
                                displayFieldWithTextField = false
                            }
                        }

                     // expand
                        false -> {

                            viewModel.activate(whatToActivate = IsActive.DailyDosage)
                            hint = DailyDosageHint.ConfirmNewName().message

                            if (measuringUnit == "") {

                                targetHeightOfTheColumn = 126
                                targetHeightOfTheSurface = 126

                                totalDailyDose = 1

                                displayFieldWithInsertedDose = false
                                displayFieldWithMeasuringUnits = true
                            }

                            else {

                                targetHeightOfTheColumn = 202
                                targetHeightOfTheSurface = 202

                                displayFieldWithInsertedDose = false

                                displayFieldWithMeasuringUnits = true
                                displayFieldWithTextField = true
                            }
                        }
                    }

                    areExpanded = !areExpanded
                },

                content = {

                    Column( // sets padding

                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),

                        content = {

                         // displays what takes, may also display the premium logo
                            Column(

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp),

                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceBetween,

                                content = {
                                    TextMedium50("Daily dose")

                                    TextSmall(hint)
                                }
                            )

                         // display inserted dose and measuring unit
                            AnimatedVisibility(

                                visible = displayFieldWithInsertedDose,
                                enter = fadeIn(animationSpec = tween(250)),
                                exit = fadeOut(animationSpec = tween(250)),

                                content = {

                                    Column(

                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(30.dp),

                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.Center,

                                        content = { TextMedium("$totalDailyDose $measuringUnit") }
                                    )
                                }
                            )

                         // display field with measuring units
                            AnimatedVisibility(

                                visible = displayFieldWithMeasuringUnits,
                                enter = fadeIn(animationSpec = tween(250)),
                                exit = fadeOut(animationSpec = tween(250)),

                                content = {

                                    Column(

                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(2.dp, White)
                                            .height(76.dp),

                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.SpaceBetween,

                                        content = { TextMedium("display field with measuring units") }
                                    )
                                }
                            )

                         // display field with text field
                            AnimatedVisibility(

                                visible = displayFieldWithTextField,
                                enter = fadeIn(animationSpec = tween(250)),
                                exit = fadeOut(animationSpec = tween(250)),

                                content = {

                                    Column(

                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(2.dp, White)
                                            .height(76.dp),

                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.SpaceBetween,

                                        content = { TextMedium("display field with text field") }
                                    )
                                }
                            )

                        }
                    )
                }
            )

            // place for the supporting text
            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .padding(start = 10.dp, end = 10.dp)
                    .border(2.dp, Color.Red),

                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,

                content = {

                    // TODO: implement if/when statements which are going to show this section
                    val descriptionOfTheError = "ErrorMessages.ExemplaryError().description"

                    AnimatedVisibility(

                        visible = true, // TODO: implement a variable which is going to determine whether the error is visible, or not
                        enter = fadeIn(animationSpec = tween(500)),
                        exit = fadeOut(animationSpec = tween(500)),

                        content = { TextSmall( content = descriptionOfTheError ) }
                    )
                }
            )

        }
    )
}

private sealed class DailyDosageHint {

    data class InsertDailyDosage (val message: String = "click to insert daily dose")
    data class EditNewName (val message: String = "click to edit daily dose")
    data class ConfirmNewName (val message: String = "click to confirm daily dose")
}