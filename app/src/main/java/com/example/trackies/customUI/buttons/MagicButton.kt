package com.example.trackies.customUI.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.ui.theme.CheckedTrackie
import com.example.trackies.ui.theme.PrimaryColor
import kotlinx.coroutines.delay

@Composable
fun MagicButton(onCheck: () -> Unit) {

    var time: Map<String, Int>? = null

    var isChecked by remember { mutableStateOf(false) }

    var isPressed by remember { mutableStateOf(false) }
    var startTime by remember { mutableStateOf(0L) }

    val targetWidth = if (isChecked) 70.dp else 110.dp
    val animatedWidth by animateDpAsState(targetValue = targetWidth)

    val targetColor = if (isChecked) CheckedTrackie else PrimaryColor
    val animatedColor by animateColorAsState(targetValue = targetColor)

    LaunchedEffect(isPressed) {
        if (isPressed) {
            startTime = System.currentTimeMillis()
            delay(2000)
            val endTime = System.currentTimeMillis()
            if (isPressed && (endTime - startTime) >= 2000) {
                isChecked = true
                onCheck()
            }
        } else {
            isChecked = false
        }
    }

    Button(

        onClick = {
            isChecked = !isChecked
            onCheck()
        },

        enabled = true,

        shape = RoundedCornerShape(18.dp),

        modifier = Modifier
            .width(animatedWidth)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            }
            .height(50.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = animatedColor,
            contentColor = White,
            disabledContentColor = PrimaryColor,
            disabledContainerColor = PrimaryColor
        ),

        content = {

            when (isChecked) {

                true -> {

                    Icon(
                        imageVector = Icons.Rounded.EmojiEvents,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                false -> {

                    if (time != null) {}

                    else {

                        Row(

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(25.dp)
                                .align(Alignment.Top),

                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Bottom,

                            content = {

                                TextMedium( content = "+2000" )
                                TextSmall( content = "ml" )
                            }
                        )
                    }
                }
            }
        }
    )
}