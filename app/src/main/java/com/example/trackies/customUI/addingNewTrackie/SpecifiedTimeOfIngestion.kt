@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package com.example.trackies.customUI.addingNewTrackie

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.ui.theme.SecondaryColor

@Preview
@Composable
fun SpecifiedTimeOfIngestion(time: String, dose: Int, measuringUnit: String) {

    Box( // surface

        modifier = Modifier

            .fillMaxWidth()
            .height(60.dp)
            .background(
                color = SecondaryColor,
                shape = RoundedCornerShape(20.dp)
            ),

        content = {

            Row( // sets padding

                modifier = Modifier
                    .fillMaxSize()
//                    .border(2.dp, Red)
                    .padding(start = 10.dp, top = 5.dp, bottom = 5.dp),


                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,

                content = {

                    Row( // sets padding

                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),

                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,

                        content = {

                            Row(

                                modifier = Modifier
                                    .fillMaxWidth(),

                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Bottom,

                                content = {

                                    TextMedium(content = "$time ")
                                    TextSmall(content = "$dose $measuringUnit")
                                }
                            )
                        }
                    )

                    Row( // displays buttons

                        modifier = Modifier
                            .width(100.dp)
//                            .border(2.dp, Blue)
                            .fillMaxHeight(),

                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,

                        content = {

                            IconButton(

                                onClick = {},

                                content = {

                                    Icon(

                                        imageVector = Icons.Rounded.Delete,
                                        contentDescription = null,
                                        tint = White
                                    )
                                }
                            )

                            IconButton(

                                onClick = {},

                                content = {

                                    Icon(

                                        imageVector = Icons.Rounded.Edit,
                                        contentDescription = null,
                                        tint = White
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