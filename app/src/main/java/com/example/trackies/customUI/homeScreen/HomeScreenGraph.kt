package com.example.trackies.customUI.homeScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.DateTimeClass
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.ui.theme.MyFonts
import com.example.trackies.ui.theme.PrimaryColor
import com.example.trackies.ui.theme.SecondaryColor
import com.example.trackies.ui.theme.White50
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenGraph(calculatedRegularity: MutableMap<String, Int>) {

    val currentDayOfWeek = DateTimeClass().getCurrentDayOfWeek()
    var activatedBar: String? by remember { mutableStateOf(currentDayOfWeek) }

    Row (

        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),

        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,

        content = {

            calculatedRegularity.forEach {

                Column(

                    modifier = Modifier
                        .weight(1f, true)
                        .height(180.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,

                    content = {

                        Column(

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp),

                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom,

                            content = {

                                val heightInDp = (it.value * 130 / 100).dp
                                val color = if (activatedBar == it.key) { PrimaryColor } else { SecondaryColor }

                                if ( activatedBar == it.key ) {

                                    Surface(

                                        modifier = Modifier
                                            .fillMaxWidth(0.95f)
                                            .height(20.dp),

                                        shape = RoundedCornerShape(5.dp),

                                        color = PrimaryColor,

                                        content = {

                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),

                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.Bottom,

                                                content = {

                                                    TextMedium(content = "${it.value}")
                                                    TextSmall(content = "%")
                                                }
                                            )
                                        }
                                    )

                                    Spacer(Modifier.height(10.dp))
                                }

                                Surface(

                                    modifier = Modifier
                                        .fillMaxWidth(0.5f)
                                        .height(heightInDp),

                                    shape = RoundedCornerShape(5.dp),

                                    color = color,

                                    onClick = { activatedBar = if (activatedBar == it.key) { null } else { it.key } },

                                    content = {}
                                )
                            }
                        )

//                      Day of week
                        Box(

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp),

                            contentAlignment = Alignment.Center,

                            content = {

                                val color = if (it.key == currentDayOfWeek) { PrimaryColor } else { White50 }

                                Text(
                                    text = "${it.key.subSequence(0, 3)}",
                                    style = MyFonts.titleMedium,
                                    color = color
                                )
                            }
                        )
                    }
                )
            }
        }
    )
}