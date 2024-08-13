package com.example.trackies.customUI.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Details
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconButtonToNavigateBetweenActivities ( icon: ImageVector, onClick: () -> Unit ) {

    IconButton(

        onClick = { onClick() },

        content = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = White,
            )
        }
    )
}

@Composable
fun IconButtonDetails ( onClick: () -> Unit ) {

    IconButton(

        onClick = { onClick() },
        content = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = White,
            )
        }
    )
}

@Composable
fun IconButtonDelete ( onClick: () -> Unit ) {

    IconButton(

        onClick = { onClick() },
        content = {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = null,
                tint = White,
            )
        }
    )
}