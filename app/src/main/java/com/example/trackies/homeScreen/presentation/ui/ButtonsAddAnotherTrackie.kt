package com.example.trackies.homeScreen.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.trackies.ui.theme.MyFonts
import com.example.trackies.ui.theme.PrimaryColor

@Composable
fun ButtonAddAnotherTrackie(onClick: () -> Unit ) {

    Button(

        onClick = { onClick() },

        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),

        shape = RoundedCornerShape(10.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor,
            contentColor = Color.White
        ),

        ) {
        Text(text = "add new trackie", style = MyFonts.titleSmall, color = Color.White)
    }
}

@Composable
fun LoadingButtonAddAnotherTrackie() {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(

                color = PrimaryColor,
                shape = RoundedCornerShape(10.dp)
            )
    )
}