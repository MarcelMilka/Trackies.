package com.example.trackies.addNewTrackie

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardReturn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.addingNewTrackie.ScheduleDays
import com.example.trackies.customUI.addingNewTrackie.SubstanceName
import com.example.trackies.customUI.addingNewTrackie.bottomBar.AddNewTrackieBottomBar
import com.example.trackies.customUI.buttons.*
import com.example.trackies.customUI.progressIndicators.AddNewTrackieProgressBar
import com.example.trackies.customUI.spacers.Spacer10
import com.example.trackies.customUI.spacers.Spacer40
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.homeScreen.presentation.HomeScreenViewState
import com.example.trackies.homeScreen.presentation.SharedViewModel
import com.example.trackies.ui.theme.*
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewTrackie(
    viewModel: SharedViewModel,
    onReturn: () -> Unit,
    onAdd: ( TrackieViewState ) -> Unit
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

    var name: String by remember { mutableStateOf("") }
    var totalDose: Int? by remember { mutableStateOf(1) }
    var measuringUnit: String? by remember { mutableStateOf("pcs") }
    var repeatOn: List<String> by remember { mutableStateOf(mutableListOf()) }
    var ingestionTime: Map<String, Int>? by remember { mutableStateOf(null) }

    var buttonAddIsEnabled by remember { mutableStateOf(false) }

    LaunchedEffect( name, totalDose, measuringUnit, repeatOn, ingestionTime ) {


        if ( name != "" && !listOfNames.contains(name) && totalDose != null && measuringUnit != null && repeatOn != null) {
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

//  linear progress indicator
    var progress by remember { mutableFloatStateOf(0f) }

    Scaffold(

        modifier = Modifier
            .fillMaxSize(),

        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },

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

                            name = ""
                            totalDose = null
                            measuringUnit = null
                            repeatOn = mutableListOf()
                            ingestionTime = null
                        },

                        onAdd = {

                            onAdd(

                                TrackieViewState(

                                    name = name,
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

                            AddNewTrackieProgressBar( currentValue = progress, goal = 4f )

                            Spacer10()

                            when (uiState) {

                                HomeScreenViewState.Loading -> { TextMedium("Loading") }

                                is HomeScreenViewState.LoadedSuccessfully -> {

                                    SubstanceName(
                                        actualName = name,
                                        namesOfAllTrackies = (uiState as HomeScreenViewState.LoadedSuccessfully).namesOfAllTrackies,
                                        onApplyNewName = {
                                            name = it
                                            if (it != "") {
                                                progress++
                                            }
                                        }
                                    )

                                    Spacer5()

                                    ScheduleDays(
                                        actualDays = repeatOn,
                                        onApplyChosenDays = {
                                            repeatOn = it
                                            if (it.isNotEmpty()) {
                                                progress++
                                            }
                                        }
                                    )
                                }

                                HomeScreenViewState.FailedToLoadData -> { TextMedium("An error occurred while accessing the database.") }
                            }
                        }
                    )
                }
            )
        }
    )
}