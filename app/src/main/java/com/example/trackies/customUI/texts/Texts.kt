package com.example.trackies.customUI.texts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.trackies.ui.theme.MyFonts

@Composable fun Header( content: String ) {
    Text(
        text = content,
        style = MyFonts.headlineLarge
    )
}

@Composable fun Detail( content: String ) {
    Text(
        text = content,
        style = MyFonts.headlineSmall
    )
}

@Composable fun TextMedium( content: String ) {
    Text(
        text = content,
        style = MyFonts.titleMedium
    )
}

@Composable fun TextSmall( content: String ) {
    Text(
        text = content,
        style = MyFonts.titleSmall
    )
}