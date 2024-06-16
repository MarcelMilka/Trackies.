package com.example.trackies.authentication.ui.login

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.example.trackies.authentication.emailPasswordCredentials.EmailPasswordCredentials
import com.example.trackies.customUI.buttons.BigStaticPrimaryButton
import com.example.trackies.customUI.buttons.SmallStaticSeondaryButton
import com.example.trackies.customUI.spacers.Spacer120
import com.example.trackies.customUI.spacers.Spacer25
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.textFields.EmailInputTextField
import com.example.trackies.customUI.textFields.PasswordInputTextField
import com.example.trackies.customUI.texts.Header
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.ui.theme.BackgroundColor

@SuppressLint("SuspiciousIndentation")
@Composable
fun LogIn(onContinue: (EmailPasswordCredentials) -> Unit, recoverThePassword: () -> Unit ) {

//  focus requesters responsible for switching between text fields
    var emailTextFieldIsActive by remember { mutableStateOf(false) }
    var focusOnEmailTextField = FocusRequester()

    var focusOnPasswordTextField = FocusRequester()
    var passwordTextFieldIsActive by remember { mutableStateOf(false) }

//  registration credentials
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

//  adjust height of the spacer to show all the ui elements
    var heightOfTheUpperSpacer by remember { mutableIntStateOf(200) }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(
                color = BackgroundColor
            ),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,

        content = {

            Spacer( modifier = Modifier
                .animateContentSize()
                .height(heightOfTheUpperSpacer.dp)
            )

            Header( "Sign in" )

            Spacer120()

            EmailInputTextField(
                insertedValue = { email = it },
                assignedFocusRequester = focusOnEmailTextField,
                isActive = {
                    emailTextFieldIsActive = it
                },
                adjustHeightOfUpperSpacer = { heightOfTheUpperSpacer = it },
                onDone = {
                    focusOnPasswordTextField.requestFocus()
                    passwordTextFieldIsActive = true
                }
            )

            Spacer5()

            PasswordInputTextField(
                insertedValue = { password = it },
                assignedFocusRequester = focusOnPasswordTextField,
                isActive = {
                    passwordTextFieldIsActive = it
                },
                adjustHeightOfUpperSpacer = { heightOfTheUpperSpacer = it },
                onDone = { focusOnPasswordTextField.requestFocus() }
            )

            Spacer25()

            BigStaticPrimaryButton( textToDisplay = "Continue.") { onContinue( EmailPasswordCredentials( email = email, password = password ) ) }

            Spacer120()

            TextSmall( content = "Have you forgotten the password?" )

            Spacer5()

            SmallStaticSeondaryButton( textToDisplay = "Recover the password" ) { recoverThePassword() }

        }
    )
}