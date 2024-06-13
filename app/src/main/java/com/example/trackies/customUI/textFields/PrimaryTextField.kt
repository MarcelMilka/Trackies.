package com.example.trackies.customUI.textFields

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.trackies.ui.theme.MyFonts
import com.example.trackies.ui.theme.PrimaryColor
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryImputTextField (
    label: String,
    keyboardType: KeyboardType,
    insertedValue: (String) -> Unit,
    assignedFocusRequester: FocusRequester,
    onDone: () -> Unit ){

    var insertedText by remember { mutableStateOf("") }

    TextField(
//      Basics
        value = insertedText,
        onValueChange = { newValue ->
            insertedValue( newValue )
            insertedText = newValue
        },

//      Appearance
        label = { Text(text = label, style = MyFonts.titleMedium) },

        modifier = Modifier
            .width(300.dp)
            .height(60.dp)
            .focusRequester( assignedFocusRequester ),

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

        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),

        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        ),

        singleLine = true,
    )
}