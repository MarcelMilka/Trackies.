package com.example.trackies.customUI.addingNewTrackie.components

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.addingNewTrackie.viewModel.AddNewTrackieViewModel
import com.example.trackies.customUI.addingNewTrackie.viewModel.IsActive
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.customUI.texts.TextMedium50
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.homeScreen.buisness.LicenseViewState
import com.example.trackies.switchToPremium.customUI.Premium
import com.example.trackies.ui.theme.SecondaryColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeOfIngestion(
    viewModel: AddNewTrackieViewModel,
    licenseViewState: LicenseViewState,
    onBuyLicense: () -> Unit
) {

    var timeOfIngestion by remember { mutableStateOf(mutableListOf<String>("")) }

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
    var displayFieldWithInsertedTimes by remember { mutableStateOf(false) }
    var displayFieldWithTextField by remember { mutableStateOf(false) }

    var hint by remember { mutableStateOf(TimeOfIngestionHint.InsertTimeOfIngestion().message) }

//  follow changes
    LaunchedEffect(viewModel.activityState.value) {

        viewModel.activityState.collect {

            if (areExpanded && viewModel.activityState.value.insertTimeOfIngestionIsActive == false) {

                if (timeOfIngestion.isEmpty()) {

                    targetHeightOfTheColumn = 50
                    targetHeightOfTheSurface = 50

                    displayFieldWithInsertedTimes = false
                    displayFieldWithTextField = false

                    hint = TimeOfIngestionHint.InsertTimeOfIngestion().message
                }

                else {

                    targetHeightOfTheColumn = 80
                    targetHeightOfTheSurface = 80

                    displayFieldWithInsertedTimes = true
                    displayFieldWithTextField = false

                    hint = TimeOfIngestionHint.EditTimeOfIngestion().message
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

                    if (licenseViewState.active) {

                        when (areExpanded) {

                            true -> { // collapse

                                viewModel.deActivate(whatToDeactivate = IsActive.TimeOfIngestion)

                                if (timeOfIngestion.isEmpty()) {

                                    targetHeightOfTheColumn = 50
                                    targetHeightOfTheSurface = 50

                                    displayFieldWithInsertedTimes = false
                                    displayFieldWithTextField = true

                                    hint = TimeOfIngestionHint.InsertTimeOfIngestion().message
                                }

                                else {

                                    CoroutineScope(Dispatchers.Default).launch {

                                        displayFieldWithTextField = false
                                        delay(250)

                                        hint = TimeOfIngestionHint.EditTimeOfIngestion().message

                                        targetHeightOfTheColumn = 80
                                        targetHeightOfTheSurface = 80
                                        displayFieldWithInsertedTimes = true
                                    }
                                }
                            }

                            false -> { // expand

                                viewModel.activate(whatToActivate = IsActive.TimeOfIngestion)
                                hint = TimeOfIngestionHint.ConfirmTimeOfIngestion().message

                                CoroutineScope(Dispatchers.Default).launch {

                                    displayFieldWithInsertedTimes = false
                                    delay(250)

                                    targetHeightOfTheColumn = 126
                                    targetHeightOfTheSurface = 126
                                    displayFieldWithTextField = true
                                }
                            }
                        }

                        areExpanded = !areExpanded
                    }

                    else { onBuyLicense() }
                },

                content = {

                    Column( // sets padding

                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {

                     // displays what takes, may also display the premium logo
                        Row(

                            modifier = Modifier
                                .fillMaxWidth()
//                                .border(2.dp, Color.White)
                                .height(30.dp),

                            horizontalArrangement = Arrangement.Start,

                            content = {

                                Column(

                                    modifier = Modifier
//                                        .border(2.dp, Color.White)
                                        .height(30.dp),

                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.SpaceBetween,

                                    content = {
                                        TextMedium50("Time of ingestion")
                                        TextSmall(hint)
                                    }
                                )

                                if (!licenseViewState.active) {

                                    Row(

                                        modifier = Modifier
                                            .weight(1f, true)
                                            .height(30.dp),

                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically,

                                        content = {

                                            Premium()
                                            Spacer(Modifier.width(5.dp))
                                        }
                                    )
                                }
                            }
                        )

                     // display inserted times
                        AnimatedVisibility(

                            visible = displayFieldWithInsertedTimes,
                            enter = fadeIn(animationSpec = tween(500)),
                            exit = fadeOut(animationSpec = tween(500)),

                            content = {

                                Column(

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(2.dp, Color.White)
                                        .height(30.dp),

                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center,

                                    content = { TextMedium("display inserted times") }
                                )
                            }
                        )

                     // display a surface with lazy list
                        AnimatedVisibility(

                            visible = displayFieldWithTextField,
                            enter = fadeIn(animationSpec = tween(250)),
                            exit = fadeOut(animationSpec = tween(250)),

                            content = {

                                Column(

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(2.dp, Color.White)
                                        .height(76.dp),

                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.SpaceBetween,

                                    content = { TextMedium("display a lazy list") }
                                )
                            }
                        )
                    }
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

private sealed class TimeOfIngestionHint {

    data class InsertTimeOfIngestion (val message: String = "click to specify time of ingestion")
    data class EditTimeOfIngestion (val message: String = "click to edit time of ingestion")
    data class ConfirmTimeOfIngestion (val message: String = "click to confirm time of ingestion")
}