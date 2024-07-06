package com.example.trackies.customUI.addingNewTrackie

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackies.customUI.buttons.MediumRadioTextButton
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.ui.theme.MyFonts
import com.example.trackies.ui.theme.SecondaryColor
import com.example.trackies.ui.theme.White50

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyDosage(
    actualMeasuringUnit: String,
    actualTotalDose: Int,

    onApplyNewMeasuringUnit: (String) -> Unit,
    onApplyNewTotalDose: (Int) -> Unit
) {

    var mlIsSelected by remember { mutableStateOf(false) }
    var gIsSelected by remember { mutableStateOf(false) }
    var pcsIsSelected by remember { mutableStateOf(false) }

    var measuringUnit by remember { mutableStateOf("") }
    var totalDose by remember { mutableIntStateOf(actualTotalDose) }

//  height of the column and the surface
    var areExpanded by remember { mutableStateOf(value = true) } // true by default, because launched effect on the first launch inverts value

//  width of the dividers
    var dividerTargetValue by remember { mutableFloatStateOf(0f) }
    var bottomDividerTargetValue by remember { mutableFloatStateOf(0f) }

//  height of the column
    var columnTargetValue by remember { mutableIntStateOf(50) }
    val columnAdditionalValue by remember { mutableIntStateOf(0) }

//  keyboard
    val focusRequester = remember { FocusRequester() }

//  height of the surface
    var surfaceTargetValue by remember { mutableIntStateOf(50) }

    LaunchedEffect(actualMeasuringUnit, actualTotalDose) {
        measuringUnit = actualMeasuringUnit
        totalDose = actualTotalDose
        if (measuringUnit == "") {

            columnTargetValue = 50
            surfaceTargetValue = 50

            mlIsSelected = false
            gIsSelected = false
            pcsIsSelected = false
        }
    }

//  adjust height of the column and surface accordingly to the value of "areExpanded" and "nameIsUnique"
    LaunchedEffect(areExpanded, measuringUnit, totalDose) {

        when (areExpanded) {

//          collapse
            true -> {

                bottomDividerTargetValue = 0f

                if (measuringUnit != "") {

                    dividerTargetValue = 1f
                    columnTargetValue = 85
                    surfaceTargetValue = 85
                }

                else {

                    dividerTargetValue = 0f
                    columnTargetValue = 50
                    surfaceTargetValue = 50
                }
            }

//          expand
            false -> {

                dividerTargetValue = 1f


                if (measuringUnit == "") {

                    bottomDividerTargetValue = 0f
                    columnTargetValue = 106
                    surfaceTargetValue = 106
                }

                else if ( measuringUnit != "" ) {

                    bottomDividerTargetValue = 1f
                    columnTargetValue = 200
                    surfaceTargetValue = 200
                }
            }
        }

        if (totalDose == 0) { columnTargetValue = (columnTargetValue + 20) } else { columnTargetValue = surfaceTargetValue }
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

    val widthOfBottomDivider = animateFloatAsState(

        targetValue = bottomDividerTargetValue,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 50,
            easing = LinearOutSlowInEasing
        ),
        label = ""
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

                    if (areExpanded) {
                        onApplyNewMeasuringUnit(measuringUnit)
                        onApplyNewTotalDose(totalDose)
                    }
                },

                content = {

                    Column(

                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),

                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,

                        content = {

//                          Text "Substance name" and divider
                            Column(

                                modifier = Modifier
                                    .fillMaxWidth()
//                                    .border(2.dp, White)
                                    .height(50.dp),

                                verticalArrangement = Arrangement.Center,

                                content = {

                                    Text( text = "Daily dosage",
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

//                          "measuring unit" and radio buttons to choose measuring unit and
                            Column(

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),

                                verticalArrangement = Arrangement.Top,

                                content = {

//                                    Dose and measuring unit
                                    AnimatedVisibility(

                                        visible = areExpanded,
                                        enter = fadeIn(animationSpec = tween(2000)),
                                        exit = fadeOut(animationSpec = tween(100))
                                    ) {

                                        Text(
                                            text = "$totalDose $measuringUnit",
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
                                            text = "choose the measuring unit",
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

                                                MediumRadioTextButton(text = "ml", isSelected = mlIsSelected) {

                                                    if (it) {

                                                        measuringUnit = "ml"

                                                        mlIsSelected = true
                                                        gIsSelected = false
                                                        pcsIsSelected = false
                                                    }
                                                }
                                                MediumRadioTextButton(text = "g", isSelected = gIsSelected) {

                                                    if (it) {

                                                        measuringUnit = "g"

                                                        gIsSelected = true
                                                        mlIsSelected = false
                                                        pcsIsSelected = false
                                                    }
                                                }
                                                MediumRadioTextButton(text = "pcs", isSelected = pcsIsSelected) {

                                                    if (it) {

                                                        measuringUnit = "pcs"

                                                        pcsIsSelected = true
                                                        gIsSelected = false
                                                        mlIsSelected = false
                                                    }
                                                }
                                            }
                                        )
                                    }
                                }
                            )

                            Divider(

                                color = White50,

                                modifier = Modifier
                                    .fillMaxWidth(widthOfBottomDivider)
                                    .padding(start = 15.dp, end = 15.dp)
                            )

//                          Inserting a dose
                            Column(

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(99.dp),

                                verticalArrangement = Arrangement.SpaceAround,

                                content = {

                                    AnimatedVisibility(

                                        visible = !areExpanded,
                                        enter = fadeIn(animationSpec = tween(2000)),
                                        exit = fadeOut(animationSpec = tween(100))
                                    ) {

                                        Text(
                                            text = "insert the daily dose",
                                            style = MyFonts.titleSmall,
                                            modifier = Modifier
                                                .padding(start = 15.dp)
                                        )

                                        TextField(

                                            value = if (totalDose == 0) "" else "$totalDose $measuringUnit",
                                            onValueChange = {
                                                val newValue = it.filter { char -> char.isDigit() }
                                                totalDose = newValue.toIntOrNull() ?: 0
                                            },

                                            singleLine = true,
                                            enabled = true,

                                            colors = TextFieldDefaults.textFieldColors(

                                                textColor = White,
                                                cursorColor = White,
                                                unfocusedLabelColor = Transparent,
                                                focusedLabelColor = Transparent,

                                                containerColor = Transparent,

                                                unfocusedIndicatorColor = Transparent,
                                                focusedIndicatorColor = Transparent
                                            ),

                                            textStyle = TextStyle.Default.copy(fontSize = 20.sp),

                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Number
                                            ),

                                            modifier = Modifier
                                                .focusRequester(focusRequester)
                                                .onGloballyPositioned { focusRequester.requestFocus() }
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

                visible = (totalDose == 0),
                enter = fadeIn(animationSpec = tween(500)),
                exit = fadeOut(animationSpec = tween(500))
            ) { Text(text = "Dose can't be equal to 0",
                style = MyFonts.titleSmall,
                modifier = Modifier.padding(start = 10.dp)
            ) }
        }
    )
}