package com.example.trackies.detailedTrackie.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.spacers.Spacer10
import com.example.trackies.ui.theme.SecondaryColor

@Composable
fun LowerPartOfUiLoading() {

    Column(

        modifier = Modifier
            .fillMaxWidth(),

        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,

        content = {

            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(30.dp),

                shape = RoundedCornerShape(20.dp),

                color = SecondaryColor
            ) {}

            Spacer10()

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),

                shape = RoundedCornerShape(20.dp),

                color = SecondaryColor
            ) {}
        }
    )
}