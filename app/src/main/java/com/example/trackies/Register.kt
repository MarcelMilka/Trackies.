package com.example.trackies

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.buttons.BigDynamicButton
import com.example.trackies.customUI.spacers.Spacer120
import com.example.trackies.customUI.spacers.Spacer25
import com.example.trackies.customUI.spacers.Spacer5
import com.example.trackies.customUI.textFields.EmailInputTextField
import com.example.trackies.customUI.textFields.PasswordInputTextField
import com.example.trackies.customUI.texts.Header
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.customUI.texts.TextSmall
import com.example.trackies.ui.theme.BackgroundColor

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun Register( navigate: (String) -> Unit ) {

//  focus requesters responsible for switching between text fields
    var emailTextFieldIsActive by remember { mutableStateOf(false) }
    var focusOnEmailTextField = FocusRequester()

    var focusOnPasswordTextField = FocusRequester()
    var passwordTextFieldIsActive by remember { mutableStateOf(false) }

//  registration credentials
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

//  "animators" of password requirements
    val displayPasswordRequirements by animateFloatAsState(
        targetValue = if (passwordTextFieldIsActive) 1.0f else 0f,
        label = "alpha"
    )
    var heightOfTheColumnWithPasswordRequirements by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        heightOfTheColumnWithPasswordRequirements = 0
    }

//  counters of letters, characters, digits and big letters
    var amountOfLetters by remember { mutableIntStateOf(0) }
    var amountOfSpecialCharacters by remember { mutableIntStateOf(0) }
    var amountOfBigLetters by remember { mutableIntStateOf(0) }
    var amountOfDigits by remember { mutableIntStateOf(0) }

//  adjust height of the spacer to show all the ui elements
    var heightOfTheUpperSpacer by remember { mutableIntStateOf(200) }

//  enable/disable the button responsible for signing in
    var buttonContinueIsEnabled by remember { mutableStateOf(false) }
    LaunchedEffect(email, password) {

        val specialCharacters = listOf("!", "@", "#", "$", "%", "^", "&", "*", "(", ")")

        amountOfBigLetters = password.count { it.isUpperCase() }
        amountOfSpecialCharacters = password.count { specialCharacters.contains(it.toString()) }
        amountOfLetters = password.length
        amountOfDigits = password.count {it.isDigit()}

        buttonContinueIsEnabled = email.isNotEmpty() &&
                amountOfLetters >= 12 &&
                amountOfSpecialCharacters >= 1 &&
                amountOfBigLetters >= 1 &&
                amountOfDigits >= 1
    }

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

            Header( "Sign up" )

            Spacer120()

            EmailInputTextField(
                insertedValue = { email = it },
                assignedFocusRequester = focusOnEmailTextField,
                isActive = {
                    emailTextFieldIsActive = it
                    heightOfTheColumnWithPasswordRequirements = 0
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
                    heightOfTheColumnWithPasswordRequirements = 70
//
//                    if (WindowInsets.isImeVisible == false) {
//                        heightOfTheUpperSpacer = 200
//                    }
                },
                adjustHeightOfUpperSpacer = { heightOfTheUpperSpacer = it },
                onDone = { focusOnPasswordTextField.requestFocus() }
            )

            Spacer5()

            Column (

                modifier = Modifier
                    .width(300.dp)
                    .animateContentSize()
                    .height(heightOfTheColumnWithPasswordRequirements.dp)
                    .graphicsLayer {
                        alpha = displayPasswordRequirements
                    },

                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,

                content = {

                    TextMedium( content = "Password requirements:")
                    TextSmall( content = "$amountOfLetters/12 characters")
                    TextSmall( content = "$amountOfSpecialCharacters/1 special character (! @ # $ % ^ & * ( ) )")
                    TextSmall( content = "$amountOfBigLetters/1 big letter")
                    TextSmall( content = "$amountOfDigits/1 digit")
                }
            )

            Spacer25()

            BigDynamicButton( textToDisplay = "Continue.", isEnabled = buttonContinueIsEnabled ) { navigate( "Authenticate" ) }

        }
    )
}