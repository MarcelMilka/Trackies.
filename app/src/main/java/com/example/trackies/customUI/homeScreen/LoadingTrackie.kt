package com.example.trackies.customUI.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.buttons.IconButtonDetails
import com.example.trackies.customUI.buttons.MagicButton
import com.example.trackies.customUI.progressIndicators.TrackieProgressBar
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.ui.theme.PrimaryColor
import com.example.trackies.ui.theme.SecondaryColor
import com.example.trackies.ui.theme.White50

@Composable
fun LoadingTrackie() {

    Row(

        modifier = Modifier

            .fillMaxWidth()
            .height(60.dp)
            .background(
                color = SecondaryColor,
                shape = RoundedCornerShape(20.dp)
            ),

        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,

        content = {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
//                    .border(2.dp, White)
                    .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                    .weight(2f, true),

                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,

                content = {

//                    TextMedium( "water" )

                    Box(
                        modifier = Modifier
                            .width(125.dp)
                            .height(18.dp)
                            .background(color = White50, shape = RoundedCornerShape(18.dp))
                    )

                    Spacer5()

                    Row(
                        modifier = Modifier
                            .height(12.dp)
                            .fillMaxWidth(),

                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Bottom,

                        content = {

                            TrackieProgressBar(currentValue = 0, goal = 1)

                            Spacer(Modifier.width(5.dp))

                            Box(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(10.dp)
                                    .background(color = White50, shape = RoundedCornerShape(18.dp))
                            )
                        }
                    )
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(50.dp),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

                content = {

                    Box(
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .background(color = White50, shape = RoundedCornerShape(5.dp))
                    )
                }
            )

            Box(
                modifier = Modifier
                    .width(110.dp)
                    .height(50.dp)
                    .background(color = PrimaryColor, shape = RoundedCornerShape(18.dp)),

                contentAlignment = Alignment.Center,

                content = {

                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(20.dp)
                            .background(color = White50, shape = RoundedCornerShape(20.dp))
                    )
                }
            )
        }
    )
}