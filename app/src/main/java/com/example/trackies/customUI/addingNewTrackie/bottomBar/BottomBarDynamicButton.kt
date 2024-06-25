package com.example.trackies.customUI.addingNewTrackie.bottomBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.example.trackies.customUI.texts.TextMedium
import com.example.trackies.ui.theme.PrimaryColor
import com.example.trackies.ui.theme.White50

@Composable
fun BottomBarDynamicButton(
    buttonAddIsEnabled: Boolean,
    onAdd: () -> Unit
) {

    AnimatedVisibility(
        visible = buttonAddIsEnabled,
        enter = fadeIn(),
        exit = fadeOut()

    ) {
        Button(

            onClick = { onAdd() },
            modifier = Modifier
                .width(120.dp)
                .fillMaxHeight(),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor,
                contentColor = White,
            ),

            enabled = buttonAddIsEnabled,

            content = {

                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = White
                )

                Spacer(Modifier.width(10.dp))

                TextMedium(  content = "add")
            }
        )
    }
}