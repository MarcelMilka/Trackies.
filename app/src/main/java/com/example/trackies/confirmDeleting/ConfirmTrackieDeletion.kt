package com.example.trackies.confirmDeleting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.homeScreen.buisness.TrackieViewState
import com.example.trackies.ui.theme.BackgroundColor
import com.example.trackies.ui.theme.PrimaryColor

@Composable
fun ConfirmTrackieDeletion(
    
    nameOfTheTrackieToDelete: TrackieViewState?,
    onConfirm: (trackieViewState: TrackieViewState) -> Unit,
    onDecline: () -> Unit
) {

    Column(

        modifier = Modifier.fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        content = {

            Box(

                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(0.2f)
                    .background(BackgroundColor, RoundedCornerShape(20.dp)),

                content = {

                    Column(

                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),

                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween,

                        content = {

                            if (nameOfTheTrackieToDelete != null) {

                                Column {

                                    TextMedium("Delete ${nameOfTheTrackieToDelete.name}?")

                                    Spacer5()

                                    TextSmall(content = "It will not be possible to retrieve it back.")
                                }

                                Row (

                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Button(

                                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                                        content = { TextMedium("Confirm") },
                                        onClick = { onConfirm(nameOfTheTrackieToDelete) }
                                    )

                                    Button(

                                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                                        content = { TextMedium("Decline") },
                                        onClick = { onDecline() }
                                    )
                                }
                            }

                            else { TextSmall(content = "An error occurred.") }
                        }
                    )
                }
            )
        }
    )
}
