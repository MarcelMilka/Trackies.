package com.example.trackies.customUI.texts

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.ui.theme.MyFonts
import com.example.trackies.ui.theme.White50

@Composable fun BigHeader(content: String ) {
    Text(
        text = content,
        style = MyFonts.headlineLarge
    )
}

@Composable fun MediumHeader(content: String ) {
    Text(
        text = content,
        style = MyFonts.headlineMedium,
    )
}

@Composable fun SmallHeader(content: String ) {
    Text(
        text = content,
        style = MyFonts.headlineMedium,
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

@Composable fun TextMedium50( content: String ) {

    Text(
        text = content,
        style = MyFonts.titleMedium,
        color = White50
    )
}

@Composable fun TextSmall( content: String) {
    Text(
        text = content,
        style = MyFonts.titleSmall
    )
}