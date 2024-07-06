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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import com.example.trackies.customUI.addingNewTrackie.viewModel.AddNewTrackieViewModel
import com.example.trackies.customUI.addingNewTrackie.viewModel.IsActive
import com.example.trackies.customUI.buttons.MediumRadioTextButton
import com.example.trackies.customUI.buttons.MediumSelectableTextButton
import com.example.trackies.customUI.texts.*
import com.example.trackies.ui.theme.SecondaryColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun ImprovedScheduleDays(
    viewModel: AddNewTrackieViewModel
) {

    var repeatOn by remember { mutableStateOf(mutableListOf<String>()) }

    var monday by remember { mutableStateOf(false) }
    var tuesday by remember { mutableStateOf(false) }
    var wednesday by remember { mutableStateOf(false) }
    var thursday by remember { mutableStateOf(false) }
    var friday by remember { mutableStateOf(false) }
    var saturday by remember { mutableStateOf(false) }
    var sunday by remember { mutableStateOf(false) }

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
    var displayFieldWithChosenDaysOfWeek by remember { mutableStateOf(false) }
    var displayFieldWithTextField by remember { mutableStateOf(false) }

    var hint by remember { mutableStateOf(ScheduleDaysHint.InsertSchedule().message) }

//  follow changes
    LaunchedEffect(viewModel.activityState.value) {

        viewModel.activityState.collect {

            if (areExpanded && viewModel.activityState.value.scheduleDaysIsActive == false) {

                if (repeatOn.isEmpty()) {

                    targetHeightOfTheColumn = 50
                    targetHeightOfTheSurface = 50

                    displayFieldWithChosenDaysOfWeek = false
                    displayFieldWithTextField = false

                    hint = ScheduleDaysHint.InsertSchedule().message
                }

                else {

                    targetHeightOfTheColumn = 80
                    targetHeightOfTheSurface = 80

                    displayFieldWithChosenDaysOfWeek = true
                    displayFieldWithTextField = false

                    hint = ScheduleDaysHint.EditSchedule().message
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

                        true -> { // collapse

                            viewModel.deActivate(whatToDeactivate = IsActive.ScheduleDays)

                            if (repeatOn.isEmpty()) {

                                hint = ScheduleDaysHint.InsertSchedule().message

                                targetHeightOfTheColumn = 50
                                targetHeightOfTheSurface = 50

                                displayFieldWithChosenDaysOfWeek = false
                                displayFieldWithTextField = true
                            }

                            else {

                                CoroutineScope(Dispatchers.Default).launch {

                                    displayFieldWithTextField = false
                                    delay(250)

                                    hint = ScheduleDaysHint.EditSchedule().message

                                    targetHeightOfTheColumn = 80
                                    targetHeightOfTheSurface = 80
                                    displayFieldWithChosenDaysOfWeek = true
                                }
                            }
                        }

                        false -> { // expand

                            viewModel.activate(whatToActivate = IsActive.ScheduleDays)
                            hint = ScheduleDaysHint.ConfirmSchedule().message

                            CoroutineScope(Dispatchers.Default).launch {

                                displayFieldWithChosenDaysOfWeek = false
                                delay(250)

                                targetHeightOfTheColumn = 106
                                targetHeightOfTheSurface = 106
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
                            .padding(10.dp)
                    ) {

                        Column( // displays what takes, may also display the premium logo

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp),

                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceBetween,

                            content = {
                                TextMedium50("Schedule days")
                                TextSmall(hint)
                            }
                        )

                        // display inserted name
                        AnimatedVisibility(

                            visible = displayFieldWithChosenDaysOfWeek,
                            enter = fadeIn(animationSpec = tween(500)),
                            exit = fadeOut(animationSpec = tween(500)),

                            content = {

                                Column(

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(30.dp),

                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center,

                                    content = {TextMedium(repeatOn.joinToString(separator = ", "))}
                                )
                            }
                        )

                        // display a text field
                        AnimatedVisibility(

                            visible = displayFieldWithTextField,
                            enter = fadeIn(animationSpec = tween(250)),
                            exit = fadeOut(animationSpec = tween(250)),

                            content = {

                                Row(

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),

                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,

                                    content = {

                                        MediumSelectableTextButton(text = "mon", isSelected = monday) {
                                            monday = it

                                            if (it) {
                                                repeatOn.add("mon")
                                            } else {
                                                if (repeatOn.contains("mon")) {
                                                    repeatOn.remove("mon")
                                                }
                                            }
                                        }

                                        MediumSelectableTextButton(text = "tue", isSelected = tuesday) {
                                            tuesday = it

                                            if (it) {
                                                repeatOn.add("tue")
                                            } else {
                                                if (repeatOn.contains("tue")) {
                                                    repeatOn.remove("tue")
                                                }
                                            }
                                        }

                                        MediumSelectableTextButton(text = "wed", isSelected = wednesday) {
                                            wednesday = it

                                            if (it) {
                                                repeatOn.add("wed")
                                            } else {
                                                if (repeatOn.contains("wed")) {
                                                    repeatOn.remove("wed")
                                                }
                                            }
                                        }

                                        MediumSelectableTextButton(text = "thu", isSelected = thursday) {
                                            thursday = it

                                            if (it) {
                                                repeatOn.add("thu")
                                            } else {
                                                if (repeatOn.contains("thu")) {
                                                    repeatOn.remove("thu")
                                                }
                                            }
                                        }

                                        MediumSelectableTextButton(text = "fri", isSelected = friday) {
                                            friday = it

                                            if (it) {
                                                repeatOn.add("fri")
                                            } else {
                                                if (repeatOn.contains("fri")) {
                                                    repeatOn.remove("fri")
                                                }
                                            }
                                        }

                                        MediumSelectableTextButton(text = "sat", isSelected = saturday) {
                                            saturday = it

                                            if (it) {
                                                repeatOn.add("sat")
                                            } else {
                                                if (repeatOn.contains("sat")) {
                                                    repeatOn.remove("sat")
                                                }
                                            }
                                        }

                                        MediumSelectableTextButton(text = "sun", isSelected = sunday) {
                                            sunday = it

                                            if (it) {
                                                repeatOn.add("sun")
                                            } else {
                                                if (repeatOn.contains("sun")) {
                                                    repeatOn.remove("sun")
                                                }
                                            }
                                        }

                                    }
                                )
                            }
                        )
                    }
                }
            )

            Row( // place for the supporting text

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


private sealed class ScheduleDaysHint {

    data class InsertSchedule (val message: String = "click to choose when you want to ingest the substance")
    data class EditSchedule (val message: String = "click to edit when you want to ingest the substance")
    data class ConfirmSchedule (val message: String = "click to confirm the choice")
}