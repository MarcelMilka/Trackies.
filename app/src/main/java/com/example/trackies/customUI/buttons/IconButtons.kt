package com.example.trackies.customUI.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
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

        modifier = Modifier
            .size( 50.dp )
            .padding( start = 20.dp, top = 20.dp),

        onClick = { onClick() },
        content = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = White,
                modifier = Modifier.size( 50.dp )
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