package com.example.trackies.customUI.buttons

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.trackies.ui.theme.*

@Composable
fun BigStaticPrimaryButton(textToDisplay: String, onClick: () -> Unit ) {

    Button(

        onClick = { onClick() },

        modifier = Modifier
            .width(250.dp)
            .height(50.dp),

        shape = RoundedCornerShape(20.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor,
            contentColor = Color.White,
        ),

    ) {
        Text(text = textToDisplay, style = MyFonts.titleMedium)
    }
}

@Composable
fun MediumStaticPrimaryButton(textToDisplay: String, enabled: Boolean, onClick: () -> Unit ) {

    Button(

        onClick = { onClick() },

        modifier = Modifier
            .width(80.dp)
            .height(50.dp),

        shape = RoundedCornerShape(20.dp),

        enabled = enabled,

        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor,
            contentColor = Color.White,
        ),

        ) {
        Text(text = textToDisplay, style = MyFonts.titleMedium)
    }
}

@Composable fun ButtonShowAllTrackies( onClick: () -> Unit ) {

    Button(

        onClick = { onClick() },

        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),

        shape = RoundedCornerShape(10.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = SecondaryColor,
            contentColor = Color.White
        ),

        ) {
        Text(text = "show all trackies", style = MyFonts.titleSmall, color = Color.White)
    }
}

@Composable fun ButtonAddAnotherTrackie( onClick: () -> Unit ) {

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