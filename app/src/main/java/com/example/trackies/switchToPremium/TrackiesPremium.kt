package com.example.trackies.switchToPremium

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackies.switchToPremium.customUI.Feature
import com.example.trackies.switchToPremium.customUI.Premium
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.Detail
import com.example.trackies.customUI.texts.TextMedium

import com.example.trackies.ui.theme.*
@Composable fun TrackiesPremium( onReturnHomeScreen: () -> Unit) {

    Column(

        modifier = Modifier.fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        content = {

            Column(

                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(0.7f)
                    .background(BackgroundColor, RoundedCornerShape(20.dp)),

                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,

                content = {

                    Column(

                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.3f)
                            .background(PrimaryColor, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),

                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,

                        content = {

                            Column(

                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp),

                                content = {

                                    Row(

                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(28.dp),

                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,

                                        content = {

                                            Row(

                                                modifier = Modifier
                                                    .height(30.dp),

                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically,

                                                content = {

                                                    Text(
                                                        text = "Trackies",
                                                        fontFamily = quickSandBold,
                                                        fontSize = 25.sp,
                                                        color = White
                                                    )

                                                    Spacer(Modifier.width(5.dp))

                                                    Premium()
                                                }
                                            )

                                            IconButton(

                                                onClick = { onReturnHomeScreen() },

                                                content = {
                                                    Icon(
                                                        imageVector = Icons.Rounded.Close,
                                                        contentDescription = null,
                                                        tint = White,
                                                    )
                                                }
                                            )
                                        }
                                    )

                                    Spacer5()

                                    Column(

                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f, true),

                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.Top,

                                        content = {

                                            Detail("Access the full potential of trackies for " +
                                                    "just $9.99 + tax/month, or with annual subscription."
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )

//                  Content on the blackish space
                    Column (

                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),

                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,

                        content = {

                            Feature(
                                feature = "",
                                freeVersion = {

                                    Text(
                                        text = "free",
                                        fontFamily = quickSandBold,
                                        fontSize = 10.sp,
                                        color = White
                                    )
                                },
                                premiumVersion = { Premium() }
                            )

                            Spacer5()

                            Divider(color = White50)

                            Spacer5()

                            Feature(
                                feature = "Limit of trackies",
                                freeVersion = { TextMedium("1") },
                                premiumVersion = { Icon(Icons.Rounded.AllInclusive, null, tint = White) }
                            )

                            Spacer5()

                            Feature(
                                feature = "Notifications",
                                freeVersion = { Icon(Icons.Rounded.Remove, null, tint = White) },
                                premiumVersion = { Icon(Icons.Rounded.Check, null, tint = White) }
                            )

                            Spacer5()

                            Feature(
                                feature = "Widgets",
                                freeVersion = { Icon(Icons.Rounded.Remove, null, tint = White) },
                                premiumVersion = { Icon(Icons.Rounded.Check, null, tint = White) }
                            )

                            Spacer5()

                            Feature(
                                feature = "Ingestion time",
                                freeVersion = { Icon(Icons.Rounded.Remove, null, tint = White) },
                                premiumVersion = { Icon(Icons.Rounded.Check, null, tint = White) }
                            )
                        }
                    )
                }
            )
        }
    )
}