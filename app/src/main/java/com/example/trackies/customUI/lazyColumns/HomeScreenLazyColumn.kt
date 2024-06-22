package com.example.trackies.customUI.lazyColumns

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.buttons.ButtonAddAnotherTrackie
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.BigHeader
import com.example.trackies.customUI.trackie.Trackie
import com.example.trackies.homeScreen.presentation.HomeScreenViewState

@Composable fun HomeScreenLazyColumn( uiState: HomeScreenViewState ) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
//            .border(2.dp, Color.White)
            .padding(start = 20.dp, end = 20.dp),

        verticalArrangement = Arrangement.Top,

        content = {

            when (uiState) {

                HomeScreenViewState.Loading -> {
                    this.item { BigHeader("Loading") }
                }

                is HomeScreenViewState.LoadedSuccessfully -> {

                    items( uiState.trackies ) {
                        Trackie()
                        Spacer5()
                    }

                    item {

                        ButtonAddAnotherTrackie {

                        }
                    }
                }

                HomeScreenViewState.FailedToLoadData -> {
                    this.item { BigHeader("Failed to load data") }
                }
            }
        }
    )
}