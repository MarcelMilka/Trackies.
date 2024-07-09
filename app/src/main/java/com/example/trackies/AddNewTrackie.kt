package com.example.trackies

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
import com.example.trackies.customUI.addingNewTrackie.bottomBar.AddNewTrackieBottomBar
import com.example.trackies.customUI.addingNewTrackie.components.ImprovedDailyDosage
import com.example.trackies.customUI.addingNewTrackie.components.ImprovedNameOfTrackie
import com.example.trackies.customUI.addingNewTrackie.components.ImprovedScheduleDays
import com.example.trackies.customUI.addingNewTrackie.components.TimeOfIngestion
import com.example.trackies.customUI.addingNewTrackie.viewModel.AddNewTrackieViewModel
import com.example.trackies.customUI.buttons.*
import com.example.trackies.customUI.spacers.Spacer40
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.MediumHeader
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.homeScreen.presentation.HomeScreenViewState
import com.example.trackies.homeScreen.presentation.SharedViewModel
import com.example.trackies.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewTrackie(
    viewModel: SharedViewModel,
    onReturn: () -> Unit,
    onNavigateToTrackiesPremium: () -> Unit,
    onAdd: ( TrackieViewState ) -> Unit
) {

//  SharedViewModel
    val uiState by viewModel.uiState.collectAsState()
    var listOfNames by remember { mutableStateOf(mutableListOf<String>()) }

    when (uiState) {
        HomeScreenViewState.Loading -> {}

        is HomeScreenViewState.LoadedSuccessfully -> {
            listOfNames = (uiState as HomeScreenViewState.LoadedSuccessfully).namesOfAllTrackies.toMutableList()
        }

        HomeScreenViewState.FailedToLoadData -> {}
    }

    val addNewTrackieViewModel = AddNewTrackieViewModel()

    var buttonAddIsEnabled by remember { mutableStateOf(false) }

    CoroutineScope(Dispatchers.Main).launch {

        addNewTrackieViewModel.buttonIsEnabled.collect { buttonAddIsEnabled = it }
    }

    Scaffold(

        modifier = Modifier.fillMaxSize(),

        bottomBar = {

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(20.dp),

                content = {

                    AddNewTrackieBottomBar(

                        buttonAddIsEnabled = buttonAddIsEnabled,

                        onClearAll = { addNewTrackieViewModel.clearAll() },

                        onAdd = {

                            CoroutineScope(Dispatchers.Main).launch {

                                addNewTrackieViewModel.viewState.collect {

                                    if (it.name != "" &&
                                        it.totalDose != 0 &&
                                        it.measuringUnit != "" &&
                                        it.repeatOn.isNotEmpty()) {

                                        onAdd(

                                            TrackieViewState(

                                                name = it.name,
                                                totalDose = it.totalDose,
                                                measuringUnit = it.measuringUnit,
                                                repeatOn = it.repeatOn,
                                                ingestionTime = it.ingestionTime
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            )
        },

        content = {

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

                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,

                        content = {

                            IconButtonToNavigateBetweenActivities(icon = Icons.AutoMirrored.Rounded.KeyboardReturn) { onReturn() }

                            Spacer40()

                            MediumHeader(content = "Add new trackie")

                            Spacer5()

                            when (uiState) {

                                HomeScreenViewState.Loading -> {}

                                is HomeScreenViewState.LoadedSuccessfully -> {

                                    ImprovedNameOfTrackie(viewModel = addNewTrackieViewModel)

                                    Spacer5()

                                    ImprovedDailyDosage(viewModel = addNewTrackieViewModel)

                                    Spacer5()

                                    ImprovedScheduleDays(viewModel = addNewTrackieViewModel)

                                    Spacer5()

                                    TimeOfIngestion(
                                        viewModel = addNewTrackieViewModel,
                                        licenseViewState = (uiState as HomeScreenViewState.LoadedSuccessfully).license,
                                        onBuyLicense = { onNavigateToTrackiesPremium() }
                                    )
                                }

                                HomeScreenViewState.FailedToLoadData -> {}

                            }
                        }
                    )
                }
            )
        }
    )
}