package com.example.goneappforandroid.compose.bottomappbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.goneappforandroid.compose.prefFirstStart

@Composable
fun CustomBottomAppBar(navHostController: NavHostController) {
    val objects = listOf(CustomBottomAppBarItems.Tasks, CustomBottomAppBarItems.Settings)
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val routes = listOf("overview", "history")

    val local = LocalContext.current

    AnimatedVisibility(visible = !prefFirstStart(local, isLoad = true), enter = slideInVertically(initialOffsetY = { it })) {
        BottomAppBar() {
            objects.forEach { mObject ->
                val selected =
                    currentDestination?.hierarchy?.any { it.route == mObject.route } == true
                NavigationBarItem(
                    icon = {
                        Icon(imageVector = mObject.image,
                            contentDescription = null,
                            tint = if (selected || (navHostController.currentDestination?.route in routes && mObject.route == CustomBottomAppBarItems.Settings.route)) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
                    },
                    selected = false,
                    onClick = {
                        if (!selected) {
                            navHostController.navigate(mObject.route) {
                                popUpTo(mObject.route) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }

}