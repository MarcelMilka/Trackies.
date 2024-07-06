package com.example.trackies.switchToPremium.customUI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackies.ui.theme.PrimaryColor
import com.example.trackies.ui.theme.quickSandBold

@Composable
fun Premium() {

    Box(
        modifier = Modifier
            .width(60.dp)
            .height(20.dp)
            .background(color = Color.White, shape = RoundedCornerShape(20.dp)),

        contentAlignment = Alignment.Center,

        content = {

            Text(
                text = "premium",
                fontFamily = quickSandBold,
                fontSize = 10.sp,
                color = PrimaryColor
            )
        }
    )
}