package com.example.trackies.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.buttons.IconButtonToNavigateBetweenActivities
import com.example.trackies.customUI.spacers.Spacer10
import com.example.trackies.customUI.spacers.Spacer40
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.texts.*
import com.example.trackies.ui.theme.BackgroundColor

@Composable
fun Settings(

    onReturnHomeScreen: () -> Unit,
    onChangeEmail: () -> Unit,
    onChangePassword: () -> Unit,
    onDeleteAccount: () -> Unit,
    onChangeLanguage: () -> Unit,
    onLogout: () -> Unit,
) {

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor),

        content = {

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .background(color = BackgroundColor),

                verticalArrangement = Arrangement.SpaceBetween,

                content = {

                    Column(

                        modifier = Modifier
                            .fillMaxWidth(),

                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,

                        content = {

                            IconButtonToNavigateBetweenActivities(icon = Icons.Rounded.Home) { onReturnHomeScreen() }

                            Spacer40()
                            MediumHeader(content = "Settings")

                            Spacer40()
                            TextMedium("Account")

                            ElementOfSettings(icon = Icons.Rounded.Email, header = "Change e-mail", detail = "marcel.milka.2006@gmail.com") { onChangeEmail() }

                            ElementOfSettings(icon = Icons.Rounded.Password, header = "Change password", detail = null) { onChangePassword() }

                            ElementOfSettings(icon = Icons.Rounded.Delete, header = "Delete account", detail = null) { onDeleteAccount() }



                            Spacer40()
                            TextMedium("Application")

                            ElementOfSettings(icon = Icons.Rounded.Language, header = "Language", detail = "English") { onChangeLanguage() }

                            ElementOfSettings(icon = Icons.Rounded.Logout, header = "Logout", detail = null) { onLogout() }
                        }
                    )
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun ElementOfSettings(icon: ImageVector, header: String, detail: String?, onClick: () -> Unit) {

    Surface(

        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),

        color = BackgroundColor,

        onClick = { onClick() },

        content = {

            Row(

                Modifier
                    .fillMaxWidth()
                    .height(35.dp),

                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,

                content = {

                    Icon(

                        imageVector = icon,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier
                            .size(37.dp)
                    )

                    Spacer( modifier = Modifier.width(5.dp) )

                    Column(

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp),

                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center ,//if (detail != null) {Arrangement.SpaceBetween} else {Arrangement.Center},

                        content = {

                            TextMedium( content = header )

                            if (detail != null) {
                                TextSmall( content = detail )
                            }
                        }
                    )
                }
            )
        }
    )
}