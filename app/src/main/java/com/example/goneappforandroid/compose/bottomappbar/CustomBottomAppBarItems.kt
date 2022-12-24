package com.example.goneappforandroid.compose.bottomappbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class CustomBottomAppBarItems(
    val route: String,
    val image: ImageVector
){
    object Tasks: CustomBottomAppBarItems(
        route = "tasks",
        image = Icons.Outlined.Menu
    )
    object Settings: CustomBottomAppBarItems(
        route = "settings",
        image = Icons.Rounded.Settings
    )

}
