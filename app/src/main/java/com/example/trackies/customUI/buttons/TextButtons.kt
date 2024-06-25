package com.example.trackies.customUI.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.example.trackies.ui.theme.MyFonts
import com.example.trackies.ui.theme.PrimaryColor

@Composable
fun MediumRadioTextButton(text: String, isSelected: Boolean, onClick: (Boolean) -> Unit ) {


    var textColor by remember { mutableStateOf(PrimaryColor) }
    textColor = when(isSelected) {
        true -> {
            PrimaryColor
        }
        false -> {
            White
        }
    }

    Text(
        text = text,
        style = MyFonts.titleMedium,
        color = textColor,
        modifier = Modifier
            .padding( end = 10.dp )
            .clickable {
                if (!isSelected) {
                    onClick( !isSelected )
                }
            }
    )
}

@Composable
fun MediumTextButton(text: String, onClick: () -> Unit ) {

    Text(
        text = text,
        style = MyFonts.titleMedium,
        color = White,
        modifier = Modifier
            .padding( end = 10.dp )
            .clickable { onClick() }
    )
}