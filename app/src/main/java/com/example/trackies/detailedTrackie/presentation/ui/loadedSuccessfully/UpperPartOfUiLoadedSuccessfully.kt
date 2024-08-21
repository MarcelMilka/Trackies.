package com.example.trackies.detailedTrackie.presentation.ui.loadedSuccessfully

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.trackies.customUI.buttons.IconButtonDelete
import com.example.trackies.customUI.spacers.Spacer10
import com.example.trackies.customUI.texts.Detail
import com.example.trackies.customUI.texts.MediumHeader
import com.example.trackies.customUI.texts.TextMedium

@Composable
fun UpperPartOfUiLoadedSuccessfully (nameOfTheTrackie: String, repeatOn: List<String>, totalDose: Int, measuringUnit: String, onDelete: () -> Unit) {

    Column(

        modifier = Modifier
            .fillMaxWidth(),

        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,

        content = {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween,

                content = {

                    MediumHeader(content = nameOfTheTrackie) //1

                    IconButtonDelete { onDelete() }
                }
            )

            Spacer10()

            TextMedium(content = "Scheduled days") //2

            Detail(content = abbreviateDaysOfWeek(repeatOn)) //3

            Spacer10() //4

            TextMedium(content = "Daily dose") //2

            Detail(content = "$totalDose $measuringUnit") //3
        }
    )
}

private fun abbreviateDaysOfWeek(list: List<String>): String {

    return list
        .map { dayOfWeek ->

            dayOfWeek.substring(0..2)
        }
        .joinToString {abbreviatedDayOfWeek ->

            abbreviatedDayOfWeek
        }

}