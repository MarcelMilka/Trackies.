package com.example.trackies.customUI.textFields

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.trackies.ui.theme.MyFonts
import com.example.trackies.ui.theme.PrimaryColor
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInputTextField (
    insertedValue: (String) -> Unit,
    assignedFocusRequester: FocusRequester,
    isActive: (Boolean) -> Unit,
    adjustHeightOfUpperSpacer: (Int) -> Unit,
    onDone: () -> Unit ) {

    var insertedText by remember { mutableStateOf("") }

    TextField(
//      Basics
        value = insertedText,
        onValueChange = { newValue ->
            insertedValue( newValue )
            insertedText = newValue
        },

//      Appearance
        label = { Text(text = "Email", style = MyFonts.titleMedium) },

        modifier = Modifier
            .width(300.dp)
            .height(60.dp)
            .focusRequester( assignedFocusRequester )
            .onFocusChanged {
                if (it.isFocused) {
                    isActive(true)
                    adjustHeightOfUpperSpacer( 170 )
                }

                else if (!it.isFocused){
                    isActive(false)
                    adjustHeightOfUpperSpacer( 200 )
                }
            },

        colors = TextFieldDefaults.textFieldColors(
            textColor = White,

            unfocusedLabelColor = White,
            focusedLabelColor = White,

            containerColor = PrimaryColor,

            cursorColor = White,

            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),


        shape = RoundedCornerShape( 20.dp ),

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            capitalization = KeyboardCapitalization.None
        ),

        keyboardActions = KeyboardActions(
            onDone = { onDone() },
        ),

        singleLine = true,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputTextField (
    insertedValue: (String) -> Unit,
    assignedFocusRequester: FocusRequester,
    isActive: (Boolean) -> Unit,
    adjustHeightOfUpperSpacer: (Int) -> Unit,
    onDone: () -> Unit ){

    var insertedText by remember { mutableStateOf("") }

    var passwordIsVisible by remember { mutableStateOf( false ) }

    var icon by remember { mutableStateOf(Icons.Rounded.Visibility) }
    LaunchedEffect( passwordIsVisible ) {
        icon = when (passwordIsVisible) {
            true -> {
                Icons.Rounded.Visibility
            }

            false -> {
                Icons.Rounded.VisibilityOff
            }
        }
    }

    TextField(
//      Basics
        value = insertedText,
        onValueChange = { newValue ->
            insertedValue( newValue )
            insertedText = newValue
        },

//      Appearance
        label = { Text(text = "Password", style = MyFonts.titleMedium) },

        modifier = Modifier
            .width(300.dp)
            .height(60.dp)
            .focusRequester( assignedFocusRequester )
            .onFocusChanged {
                if (it.isFocused) {
                    isActive(true)
                    adjustHeightOfUpperSpacer(50)
                }

                else if (!it.isFocused){
                    isActive(false)
                    adjustHeightOfUpperSpacer(200)
                }
            },

        colors = TextFieldDefaults.textFieldColors(
            textColor = White,

            unfocusedLabelColor = White,
            focusedLabelColor = White,

            containerColor = PrimaryColor,

            cursorColor = White,

            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),

        shape = RoundedCornerShape( 20.dp ),

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            capitalization = KeyboardCapitalization.None
        ),

        keyboardActions = KeyboardActions(
            onDone = { onDone() },
        ),

        trailingIcon = {
            IconButton(
                onClick = {
                    passwordIsVisible = !passwordIsVisible
                },
                content = {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = White
                    )
                },

            )
        },

        singleLine = true,

        visualTransformation = if (passwordIsVisible) VisualTransformation.None else PasswordVisualTransformation(),
    )
}