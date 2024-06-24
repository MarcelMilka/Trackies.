package com.example.trackies.customUI.lazyColumns

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.buttons.ButtonAddAnotherTrackie
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.MediumHeader
import com.example.trackies.customUI.trackie.Trackie
import com.example.trackies.homeScreen.buisness.LicenseViewState
import com.example.trackies.homeScreen.presentation.HomeScreenViewState

@Composable fun HomeScreenLazyColumn(
    uiState: HomeScreenViewState,
    passLicenseInformation: (LicenseViewState) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(start = 20.dp, end = 20.dp),

        verticalArrangement = Arrangement.Top,

        content = {

            when (uiState) {

                HomeScreenViewState.Loading -> {
                    this.item { MediumHeader("loading") }
                }

                is HomeScreenViewState.LoadedSuccessfully -> {

                    items( uiState.trackies ) {trackie ->
                        Trackie(
                            name = trackie.name!!,
                            totalDose = trackie.totalDose!!,
                            measuringUnit = trackie.measuringUnit!!,
                            repeatOn = trackie.repeatOn!!,
                            ingestionTime = trackie.ingestionTime
                        )
                        Spacer5()
                    }

                    item {

                        ButtonAddAnotherTrackie { passLicenseInformation(uiState.license) }
                    }
                }

                HomeScreenViewState.FailedToLoadData -> {
                    this.item { MediumHeader("fialed to load data") }
                }
            }
        }
    )
}