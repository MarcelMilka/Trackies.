package com.example.trackies

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.KeyboardReturn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.buttons.ButtonAddAnotherTrackie
import com.example.trackies.customUI.buttons.ButtonShowAllTrackies
import com.example.trackies.customUI.buttons.IconButtonDelete
import com.example.trackies.customUI.buttons.IconButtonToNavigateBetweenActivities
import com.example.trackies.customUI.homeScreen.HomeScreenGraph
import com.example.trackies.customUI.homeScreen.RowWithRadioButtons
import com.example.trackies.customUI.lazyColumns.HomeScreenLazyColumn
import com.example.trackies.customUI.spacers.Spacer10
import com.example.trackies.customUI.spacers.Spacer40
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.MediumHeader
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.homeScreen.presentation.SharedViewModelViewState
import com.example.trackies.ui.theme.*

@Composable
fun DetailedTrackie(

    uiState: SharedViewModelViewState,
    trackieToDisplay: TrackieViewState?,
    onReturn: () -> Unit,
    onDelete: () -> Unit
) {

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor),

        content = {

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .background(color = BackgroundColor),

                verticalArrangement = Arrangement.SpaceBetween,

                content = {

                    Column(

                        modifier = Modifier
                            .fillMaxWidth(),

                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,

                        content = {

                            IconButtonToNavigateBetweenActivities(icon = Icons.Rounded.KeyboardReturn) { onReturn() }

                            Spacer40()

                            if (trackieToDisplay != null) {

                                Row(

                                    modifier = Modifier
                                        .fillMaxWidth(),

                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,

                                    content = {

                                        MediumHeader(content = if (trackieToDisplay != null) { trackieToDisplay.name } else {"An error occurred."})

                                        IconButtonDelete { onDelete() }
                                    }
                                )

                                Spacer5()

                                TextWithButton(
                                    icon = Icons.Rounded.Edit,
                                    content = "Scheduled days",
                                    onEdit = {}
                                )

                                Row(

                                    modifier = Modifier
                                        .fillMaxWidth(),

                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,

                                    content = {

                                        val abbreviatedDaysOfWeek= trackieToDisplay.repeatOn.toMutableList().map { dayOfWeek ->

                                            dayOfWeek.substring(0..2)
                                        }

                                        if (abbreviatedDaysOfWeek.count() != 1) {

                                            abbreviatedDaysOfWeek.take(abbreviatedDaysOfWeek.count() - 1).forEach { dayOfWeek ->

                                                TextSmall(content = "$dayOfWeek, ")
                                            }

                                            TextSmall(content = "${abbreviatedDaysOfWeek[abbreviatedDaysOfWeek.count() - 1]}")
                                        }

                                        else {
                                            TextSmall(content = "$abbreviatedDaysOfWeek")
                                        }
                                    }
                                )

                                Spacer10()

                                TextWithButton(
                                    icon = Icons.Rounded.Edit,
                                    content = "Daily dose",
                                    onEdit = {}
                                )

                                Row(

                                    modifier = Modifier
                                        .fillMaxWidth(),

                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.Bottom,

                                    content = {

                                        TextMedium(content = "${trackieToDisplay.totalDose}")
                                        TextSmall(content = "${trackieToDisplay.measuringUnit}")
                                    }
                                )

                                Spacer40()

                            }

                            else { TextMedium("An error occurred while loading data or something like that, implement something cool here.") }
                        }
                    )

                    Column(

                        modifier = Modifier
                            .fillMaxWidth(),

                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Bottom,

                        content = {

                            Spacer40()

                            MediumHeader(content = "Weekly regularity")

                            Spacer5()

                            DetailedTrackieGraph( uiState = uiState, nameOfTheTrackie = "name" )
                        }
                    )
                }
            )
        }
    )
}

@Composable
private fun TextWithButton(icon: ImageVector, content: String, onEdit: () -> Unit) {

    Row(

        modifier = Modifier
            .fillMaxWidth(),

        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,

        content = {

            TextMedium(content = content)

            Spacer(modifier = Modifier.width(5.dp))

//            IconButton(
//
//                onClick = { onEdit() },
//
//                content = {
//
//                Icon(
//                        imageVector = icon,
//                        tint = White,
//                        contentDescription = null
//                    )
//                }
//            )
        }
    )
}

@Composable
private fun DetailedTrackieGraph(uiState: SharedViewModelViewState, nameOfTheTrackie: String) {

    val currentDayOfWeek = DateTimeClass().getCurrentDayOfWeek()
    var activatedBar: String? by remember { mutableStateOf(currentDayOfWeek) }

    Row (

        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),

        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,

        content = {

            when (uiState) {

                SharedViewModelViewState.Loading -> {
                    TextMedium("loading")
                }

                is SharedViewModelViewState.LoadedSuccessfully -> {
                    TextMedium("loaded")
                }

                SharedViewModelViewState.FailedToLoadData -> {
                    TextMedium("failed to load")
                }
            }
        }
    )
}