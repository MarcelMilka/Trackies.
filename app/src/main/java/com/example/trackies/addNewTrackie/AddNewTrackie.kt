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
import com.example.trackies.customUI.addingNewTrackie.bottomBar.AddNewTrackieBottomBar
import com.example.trackies.customUI.buttons.*
import com.example.trackies.customUI.spacers.Spacer40
import com.example.trackies.homeScreen.presentation.SharedViewModel
import com.example.trackies.ui.theme.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewTrackie(
    viewModel: SharedViewModel,
    onReturn: () -> Unit
) {

    val listOfNames = listOf("water", "creatine")

    var name by remember { mutableStateOf("") }
    var totalDose by remember { mutableIntStateOf(0) }
    var measuringUnit by remember { mutableStateOf("") }
    var repeatOn: List<String>? by remember { mutableStateOf(null) }
    var ingestionTime: Map<String, Int>? by remember { mutableStateOf(mapOf<String, Int>()) }

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(

        modifier = Modifier
            .fillMaxSize(),

        bottomBar = {

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(20.dp),

                content = {

                    AddNewTrackieBottomBar(

                        buttonAddIsEnabled = true,

                        onClearAll = {},
                        onAdd = {}
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

                        content = {}
                    )
                }
            )
        }
    )
}