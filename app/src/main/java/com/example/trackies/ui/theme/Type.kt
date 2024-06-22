package com.example.trackies.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.trackies.R

val quickSand = FontFamily(
    Font(R.font.quicksand_light),
    Font(R.font.quicksand_regular),
    Font(R.font.quicksand_medium),
)

// Set of Material typography styles to start with
val MyFonts = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

//  Headline
    headlineLarge = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.W700,
        fontSize = 40.sp,
        color = White
    ),

    //  Headline
    headlineMedium = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.W700,
        fontSize = 25.sp,
        color = White
    ),

//  DetailBig
    headlineSmall = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Light,
        fontSize = 15.sp,
        color = White
    ),

//  TextMedium
    titleMedium = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.W700,
        fontSize = 15.sp,
        color = White
    ),

//  TextMedium
    titleSmall = TextStyle(
        fontFamily = quickSand,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        color = White
    ),
)