package com.example.trackies.addNewTrackie

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardReturn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.addingNewTrackie.SubstanceName
import com.example.trackies.customUI.addingNewTrackie.bottomBar.AddNewTrackieBottomBar
import com.example.trackies.customUI.buttons.*
import com.example.trackies.customUI.spacers.Spacer40
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.homeScreen.presentation.HomeScreenViewState
import com.example.trackies.homeScreen.presentation.SharedViewModel
import com.example.trackies.ui.theme.*
import kotlinx.coroutines.launch
import org.checkerframework.checker.units.qual.Substance

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewTrackie(
    viewModel: SharedViewModel,
    onReturn: () -> Unit,
    onAdd: (TrackieViewState) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    var listOfNames: MutableList<String> by remember { mutableStateOf(mutableListOf()) }
    when (uiState) {
        HomeScreenViewState.Loading -> {}

        is HomeScreenViewState.LoadedSuccessfully -> {
            listOfNames = (uiState as HomeScreenViewState.LoadedSuccessfully).namesOfAllTrackies.toMutableList()
        }

        HomeScreenViewState.FailedToLoadData -> {}
    }

    var name: String? by remember { mutableStateOf(null) }
    var totalDose: Int? by remember { mutableStateOf(5) }
    var measuringUnit: String? by remember { mutableStateOf("ml") }
    var repeatOn: List<String>? by remember { mutableStateOf(mutableListOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday")) }
    var ingestionTime: Map<String, Int>? by remember { mutableStateOf(mapOf<String, Int>("10:00" to 5)) }

    var buttonAddIsEnabled by remember { mutableStateOf(false) }

    LaunchedEffect( name, totalDose, measuringUnit, repeatOn, ingestionTime ) {

        Log.d("chuuuj", "$name")

        if (name != null && !listOfNames.contains(name) && totalDose != null && measuringUnit != null && repeatOn != null && ingestionTime != null) {
            buttonAddIsEnabled = true
        }

        else if (listOfNames.contains(name)) {

            scope.launch {
                snackBarHostState.showSnackbar("Trackie of name $name already exists.")
            }
            buttonAddIsEnabled = false
        }

        else {
            buttonAddIsEnabled = false
        }
    }

    Scaffold(

        modifier = Modifier
            .fillMaxSize(),

        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },

        bottomBar = {

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(20.dp),

                content = {

                    AddNewTrackieBottomBar(

                        buttonAddIsEnabled = buttonAddIsEnabled,

                        onClearAll = {

                            name = null
                            totalDose = null
                            measuringUnit = null
                            repeatOn = null
                            ingestionTime = null
                        },

                        onAdd = {

                            onAdd(

                                TrackieViewState(

                                    name = name!!,
                                    totalDose = totalDose!!,
                                    measuringUnit = measuringUnit!!,
                                    repeatOn = repeatOn!!,
                                    ingestionTime = ingestionTime
                                )
                            )
                        }
                    )
                }
            )
        },

        content = {

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = BackgroundColor
                    ),

                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,

                content = {

                    IconButtonToNavigateBetweenActivities(icon = Icons.AutoMirrored.Rounded.KeyboardReturn) { onReturn() }

                    Spacer40()

                    Column(

                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),

                        content = {

                            SubstanceName {
                                name = it
                            }
                        }
                    )
                }
            )
        }
    )
}