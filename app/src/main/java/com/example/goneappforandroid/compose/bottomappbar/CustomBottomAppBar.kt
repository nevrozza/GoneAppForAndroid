package com.example.goneappforandroid.compose.bottomappbar

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun CustomBottomAppBar(navHostController: NavHostController){
    val objects = listOf(CustomBottomAppBarItems.tasks, CustomBottomAppBarItems.settings)
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    //contentColor = MaterialTheme.colorScheme.surface
    NavigationBar() {
        objects.forEach{mObject ->
            val selected = currentDestination?.hierarchy?.any { it.route == mObject.route } == true
            NavigationBarItem(
                icon = { Icon(imageVector = mObject.image, contentDescription = null, tint = if(selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)},
                selected = false,
                onClick = {
                    if(!selected){
                        navHostController.navigate(mObject.route){
                            popUpTo(mObject.route){
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