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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
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
@Composable fun ImprovedNameOfTrackie(
    viewModel: AddNewTrackieViewModel
) {

    var nameOfTheTrackie by remember { mutableStateOf("") }

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
    var displayFieldWithInsertedName by remember { mutableStateOf(false) }
    var displayFieldWithTextField by remember { mutableStateOf(false) }

//  focus requester
    val focusRequester = remember { FocusRequester() }

    var hint by remember { mutableStateOf(NameOfTrackieHint.InsertNewName().message) }

//  follow changes
    LaunchedEffect(viewModel.activityState.value) {

        viewModel.activityState
            .collect {

            if (areExpanded && viewModel.activityState.value.nameOfTrackieIsActive == false) {

                if (nameOfTheTrackie == "") {

                    targetHeightOfTheColumn = 50
                    targetHeightOfTheSurface = 50

                    displayFieldWithInsertedName = false
                    displayFieldWithTextField = false

                    hint = NameOfTrackieHint.InsertNewName().message
                }

                else {

                    targetHeightOfTheColumn = 80
                    targetHeightOfTheSurface = 80

                    displayFieldWithInsertedName = true
                    displayFieldWithTextField = false

                    hint = NameOfTrackieHint.EditNewName().message
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

                            viewModel.deActivate(whatToDeactivate = IsActive.NameOfTrackie)

                            if (nameOfTheTrackie == "") {

                                hint = NameOfTrackieHint.InsertNewName().message

                                targetHeightOfTheColumn = 50
                                targetHeightOfTheSurface = 50

                                displayFieldWithInsertedName = false
                                displayFieldWithTextField = true
                            }

                            else {

                                CoroutineScope(Dispatchers.Default).launch {

                                    displayFieldWithTextField = false
                                    delay(250)

                                    hint = NameOfTrackieHint.EditNewName().message

                                    targetHeightOfTheColumn = 80
                                    targetHeightOfTheSurface = 80
                                    displayFieldWithInsertedName = true
                                }
                            }
                        }

                     // expand
                        false -> {

                            viewModel.activate(whatToActivate = IsActive.NameOfTrackie)

                            CoroutineScope(Dispatchers.Default).launch {

                                displayFieldWithInsertedName = false
                                delay(250)

                                hint = NameOfTrackieHint.ConfirmNewName().message

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
                                TextMedium50("Name of trackie")

                                TextSmall(hint)
                            }
                        )

                        // display inserted name
                        AnimatedVisibility(

                            visible = displayFieldWithInsertedName,
                            enter = fadeIn(animationSpec = tween(500)),
                            exit = fadeOut(animationSpec = tween(500)),

                            content = {

                                Column(

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(30.dp),

                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center,

                                    content = { TextMedium(nameOfTheTrackie) }
                                )
                            }
                        )

                        // display a text field
                        AnimatedVisibility(

                            visible = displayFieldWithTextField,
                            enter = fadeIn(animationSpec = tween(250)),
                            exit = fadeOut(animationSpec = tween(250)),

                            content = {

                                Column(

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),

                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.SpaceBetween,

                                    content = {

                                        TextField(

                                            value = nameOfTheTrackie,
                                            onValueChange = { nameOfTheTrackie = it },

                                            singleLine = true,

                                            colors = TextFieldDefaults.textFieldColors(

                                                textColor = White,
                                                cursorColor = White,
                                                unfocusedLabelColor = White,
                                                focusedLabelColor = Color.Transparent,

                                                containerColor = Color.Transparent,

                                                unfocusedIndicatorColor = Color.Transparent,
                                                focusedIndicatorColor = Color.Transparent
                                            ),

                                            textStyle = TextStyle.Default.copy(fontSize = 20.sp),

                                            modifier = Modifier
                                                .focusRequester(focusRequester)
                                                .onGloballyPositioned { focusRequester.requestFocus() }
                                        )
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

private sealed class NameOfTrackieHint {

    data class InsertNewName (val message: String = "click to insert name of the new trackie")
    data class EditNewName (val message: String = "click to edit name of the new trackie")
    data class ConfirmNewName (val message: String = "click to confirm name of the new trackie")
}