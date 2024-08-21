package com.example.trackies.homeScreen.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.buttons.MediumRadioTextButton
import com.example.trackies.enumClasses.HomeScreenGraphToDisplay
import com.example.trackies.ui.theme.SecondaryColor

@Composable
fun RowWithRadioButtonsLoadedSuccessfully(graphToDisplay: HomeScreenGraphToDisplay, changeGraphToDisplay: (HomeScreenGraphToDisplay) -> Unit ) {

    var displayWeeklyChart by remember { mutableStateOf(true) }
    var displayMonthlyChart by remember { mutableStateOf(false) }
    var displayYearlyChart by remember { mutableStateOf(false) }

    when (graphToDisplay) {

        HomeScreenGraphToDisplay.Weekly -> {

            displayWeeklyChart = true
            displayMonthlyChart = false
            displayYearlyChart = false
        }

        HomeScreenGraphToDisplay.Monthly -> {

            displayWeeklyChart = false
            displayMonthlyChart = true
            displayYearlyChart = false
        }

        HomeScreenGraphToDisplay.Yearly -> {

            displayWeeklyChart = false
            displayMonthlyChart = false
            displayYearlyChart = true
        }
    }

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),

        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        MediumRadioTextButton(text = "weekly", isSelected = displayWeeklyChart) {

            displayWeeklyChart = it
            displayMonthlyChart = !it
            displayYearlyChart = !it

            changeGraphToDisplay(HomeScreenGraphToDisplay.Weekly)
        }

        MediumRadioTextButton(text = "monthly", isSelected = displayMonthlyChart) {

            displayMonthlyChart = it
            displayWeeklyChart = !it
            displayYearlyChart = !it

            changeGraphToDisplay(HomeScreenGraphToDisplay.Monthly)
        }

        MediumRadioTextButton(text = "yearly", isSelected = displayYearlyChart) {

            displayYearlyChart = it
            displayWeeklyChart = !it
            displayMonthlyChart = !it

            changeGraphToDisplay(HomeScreenGraphToDisplay.Yearly)
        }
    }
}

@Composable
fun RowWithRadioButtonsLoading() {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),

        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        repeat(3) {

            Spacer(Modifier.width(10.dp))

            Box(

                modifier = Modifier
                    .width(45.dp)
                    .height(15.dp)
                    .background(

                        color = SecondaryColor,
                        shape = RoundedCornerShape(10.dp)
                    )
            )
        }
    }
}