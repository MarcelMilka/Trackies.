package com.example.trackies.customUI.buttons

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.trackies.ui.theme.PrimaryColor

@Composable
fun MagicButton(isChecked: Boolean, onCheck: () -> Unit) {

    val targetWidth = if (isChecked) 50.dp else 110.dp

    val animatedWidth by animateDpAsState(targetValue = targetWidth)

    Button(

        onClick = { onCheck() },

        enabled = !isChecked,

        shape = RoundedCornerShape(20.dp),

        modifier = Modifier
            .width(animatedWidth)
            .height(50.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor,
            contentColor = Color.White,
            disabledContentColor = PrimaryColor,
            disabledContainerColor = PrimaryColor
        ),

        content = {}
    )
}