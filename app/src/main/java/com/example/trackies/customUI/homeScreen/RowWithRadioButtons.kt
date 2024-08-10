package com.example.trackies.customUI.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.buttons.MediumRadioTextButton

@Composable fun RowWithRadioButtons(graphToDisplay: GraphToDisplay, changeGraphToDisplay: (GraphToDisplay) -> Unit ) {

    var displayWeeklyChart by remember { mutableStateOf(true) }
    var displayMonthlyChart by remember { mutableStateOf(false) }
    var displayYearlyChart by remember { mutableStateOf(false) }

    when (graphToDisplay) {

        GraphToDisplay.Weekly -> {

            displayWeeklyChart = true
            displayMonthlyChart = false
            displayYearlyChart = false
        }

        GraphToDisplay.Monthly -> {

            displayWeeklyChart = false
            displayMonthlyChart = true
            displayYearlyChart = false
        }

        GraphToDisplay.Yearly -> {

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

            changeGraphToDisplay(GraphToDisplay.Weekly)
        }

        MediumRadioTextButton(text = "monthly", isSelected = displayMonthlyChart) {

            displayMonthlyChart = it
            displayWeeklyChart = !it
            displayYearlyChart = !it

            changeGraphToDisplay(GraphToDisplay.Monthly)
        }

        MediumRadioTextButton(text = "yearly", isSelected = displayYearlyChart) {

            displayYearlyChart = it
            displayWeeklyChart = !it
            displayMonthlyChart = !it

            changeGraphToDisplay(GraphToDisplay.Yearly)
        }
    }
}

enum class GraphToDisplay {
    Weekly,
    Monthly,
    Yearly
}