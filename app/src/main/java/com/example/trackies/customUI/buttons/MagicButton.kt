package com.example.trackies.customUI.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material3.*
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
import kotlinx.coroutines.*

@Composable
fun MagicButton(
    ingestionTime: Map<String, Int>?,
    totalDose: Int,
    measuringUnit: String,

    onCheck: () -> Unit
) {

    var isChecked by remember { mutableStateOf(false) }

    var targetWidthOfButton by remember { mutableIntStateOf(110) } // 70 or 110
    val animatedWidthOfButton by animateIntAsState(
        targetValue = targetWidthOfButton,
        animationSpec = tween(
            durationMillis = 250,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ),
        label = "",
    )

    val targetColorOfButton = if (isChecked) CheckedTrackie else PrimaryColor
    val animatedColorOfButton by animateColorAsState(targetValue = targetColorOfButton)

    var buttonIsHeld by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    var widthOfSurface by remember { mutableIntStateOf(0) }

    var heightOfSurface by remember { mutableIntStateOf(0) }

    Surface(

        color = animatedColorOfButton,
        shape = RoundedCornerShape(18.dp),

        modifier = Modifier
            .width(animatedWidthOfButton.dp)
            .height(50.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {

                        if (animatedWidthOfButton != 70) {

                            buttonIsHeld = true
                            val startTime = System.currentTimeMillis()

                            val coroutineScope = CoroutineScope(Dispatchers.IO).launch {

                                launch {

                                    delay(2000)
                                    if (buttonIsHeld) { isChecked = true }
                                }

                                launch {

                                    while (buttonIsHeld) {

                                        if ((System.currentTimeMillis() - startTime) >= 2000L) {

                                            isChecked = true
                                            targetWidthOfButton = 70
                                            cancel()
                                        }

                                        else {

                                            widthOfSurface = (((System.currentTimeMillis() - startTime) * 70) / 2000L).toInt()
                                            heightOfSurface = (((System.currentTimeMillis() - startTime) * 50) / 2000L).toInt()
                                        }
                                    }

                                    if (!buttonIsHeld) {

                                        widthOfSurface = 0
                                        heightOfSurface = 0
                                    }
                                }
                            }

                            tryAwaitRelease()
                            coroutineScope.cancel()
                            buttonIsHeld = false
                        }
                    }
                )
            },

        content = {

            Box(

                modifier = Modifier.fillMaxSize(),

                contentAlignment = Alignment.Center,

                content = {

//                  Orange surface which expands while holding the button
                    Surface(

                        modifier = Modifier
                            .width(widthOfSurface.dp)
                            .height(heightOfSurface.dp),

                        color = CheckedTrackie,

                        shape = RoundedCornerShape(18.dp),

                        content = {}
                    )

//                  Dose for today
                    AnimatedVisibility(

                        visible = !isChecked,
                        enter = fadeIn(animationSpec = tween(250)),
                        exit = fadeOut(animationSpec = tween(250)),

                        content = {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),

                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.Bottom,

                                content = {

                                    TextMedium(content = "+$totalDose")
                                    TextSmall(content = measuringUnit)
                                }
                            )
                        }
                    )

//                  Icon which shows that the goal has been achieved for today
                    AnimatedVisibility(

                        visible = isChecked,
                        enter = fadeIn(animationSpec = tween(250)),
                        exit = fadeOut(animationSpec = tween(250)),

                        content = {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),

                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.Bottom,

                                content = {

                                    Icon(

                                        imageVector = Icons.Rounded.EmojiEvents,
                                        tint = White,
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}