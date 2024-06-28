package com.example.trackies.customUI.addingNewTrackie

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackies.ui.theme.MyFonts
import com.example.trackies.ui.theme.SecondaryColor
import com.example.trackies.ui.theme.White50

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubstanceName(
    actualName: String,
    namesOfAllTrackies: List<String>,
    onApplyNewName: (String) -> Unit
) {

    var insertedNameIsVisible by remember { mutableStateOf(false) }
    var textFieldIsVisible by remember { mutableStateOf(false) }

//  width of the divider
    var dividerTargetValue by remember { mutableFloatStateOf(0f) }

//  name of the new trackie
    var name by remember { mutableStateOf(value = actualName) }
    var nameIsUnique by remember { mutableStateOf(value = true) }

//  height of the column and the surface
    var areExpanded by remember { mutableStateOf(value = true) } // true by default, because launched effect on the first launch inverts value

//  height of the column
    var columnTargetValue by remember { mutableIntStateOf(50) }
    val columnAdditionalValue by remember { mutableIntStateOf(0) }

//  height of the surface
    var surfaceTargetValue by remember { mutableIntStateOf(50) }
    LaunchedEffect(actualName) {
        name = actualName
        if (name == "") {
            columnTargetValue = 50
            surfaceTargetValue = 50
        }
    }

//  adjust height of the column and surface accordingly to the value of "areExpanded" and "nameIsUnique"
    LaunchedEffect(areExpanded) {

        when (areExpanded) {

            true -> {

                if (name != "") {

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


                columnTargetValue = when (nameIsUnique) {

                    true -> { surfaceTargetValue }

                    false -> { surfaceTargetValue + 20 }
                }
            }

            false -> {

                insertedNameIsVisible = false
                textFieldIsVisible = true

                dividerTargetValue = 1f
                columnTargetValue = 106
                surfaceTargetValue = 106

                columnTargetValue = when (nameIsUnique) {

                    true -> { surfaceTargetValue }

                    false -> { surfaceTargetValue + 20 }
                }
            }
        }
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

//  keyboard
    val focusRequester = remember { FocusRequester() }

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

                    if (areExpanded) { onApplyNewName(name) }
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
                                    .height(50.dp),

                                verticalArrangement = Arrangement.Center,

                                content = {

                                    Text( text = "Substance name",
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

//                          InputTextField or inserted name of a new trackie
                            Column(

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),

                                verticalArrangement = Arrangement.Top,

                                content = {

//                                  Text
                                    AnimatedVisibility(

                                        visible = areExpanded,
                                        enter = fadeIn(animationSpec = tween(2000)),
                                        exit = fadeOut(animationSpec = tween(100))
                                    ) {
                                        Text(
                                            text = name,
                                            style = MyFonts.titleMedium,
                                            modifier = Modifier
                                                .padding(start = 15.dp)
                                        )
                                    }

//                                  TextField
                                    AnimatedVisibility(

                                        visible = !areExpanded,
                                        enter = fadeIn(animationSpec = tween(2000)),
                                        exit = fadeOut(animationSpec = tween(100))
                                    ) {

                                        TextField(

                                            value = name,
                                            onValueChange = {

                                                name = it

                                                if (namesOfAllTrackies.contains(name)) { nameIsUnique = false }
                                                else { nameIsUnique = true }
                                            },

                                            singleLine = true,
                                            enabled = true,

                                            colors = TextFieldDefaults.textFieldColors(

                                                textColor = White,
                                                cursorColor = White,
                                                unfocusedLabelColor = White,
                                                focusedLabelColor = Transparent,

                                                containerColor = Transparent,

                                                unfocusedIndicatorColor = Transparent,
                                                focusedIndicatorColor = Transparent
                                            ),

                                            textStyle = TextStyle.Default.copy(fontSize = 20.sp),

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

                visible = !nameIsUnique,
                enter = fadeIn(animationSpec = tween(500)),
                exit = fadeOut(animationSpec = tween(500))
            ) { Text(text = "Trackie of name $name already exists",
                    style = MyFonts.titleSmall,
                    modifier = Modifier.padding(start = 10.dp)
                ) }
        }
    )
}
