package com.example.trackies.customUI.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.trackies.ui.theme.MyFonts
import com.example.trackies.ui.theme.SecondaryColor

@Composable
fun BigStaticSeondaryButton(textToDisplay: String, onClick: () -> Unit ) {

    Button(

        onClick = { onClick() },

        modifier = Modifier
            .width(250.dp)
            .height(50.dp),

        shape = RoundedCornerShape(20.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = SecondaryColor,
            contentColor = Color.White,
        ),

        ) {
        Text(text = textToDisplay, style = MyFonts.titleMedium)
    }
}

@Composable
fun SmallStaticSeondaryButton(textToDisplay: String, onClick: () -> Unit ) {

    Button(

        onClick = { onClick() },

        modifier = Modifier
            .width(180.dp)
            .height(40.dp),

        shape = RoundedCornerShape(20.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = SecondaryColor,
            contentColor = Color.White,
        ),

        ) {
        Text(text = textToDisplay, style = MyFonts.titleSmall)
    }
}