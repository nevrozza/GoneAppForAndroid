package com.example.goneappforandroid.compose.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SettingsScreen(navHostController: NavHostController) {
    val currentState = remember { MutableTransitionState(false) }
    currentState.targetState = true

    var isNotification = remember {
        mutableStateOf(false)
    }
    AnimatedVisibility(visibleState = currentState, enter = fadeIn(tween(800))) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier
                .height(70.dp)
                .clickable() { }) {
                MenuItem(modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxWidth(),
                    icon = Icons.Rounded.TaskAlt, iconSize = 30.dp,
                    text = "Weekly Overview",
                    icon2 = Icons.Rounded.ArrowForwardIos)
            }
            Box(contentAlignment = Alignment.Center, modifier = Modifier
                .height(70.dp)
                .clickable() {isNotification.value = !isNotification.value }) {
            MenuItem(modifier = Modifier
                .padding(start = 10.dp, end = 20.dp)
                .fillMaxWidth(),
                icon = Icons.Outlined.Notifications, iconSize = 32.dp,
                text = "Notifications",
                isNotification = isNotification)
            }
            Box(contentAlignment = Alignment.Center, modifier = Modifier
                .height(70.dp)
                .clickable() { }) {
                MenuItem(modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth(),
                    icon = Icons.Outlined.PersonAdd, iconSize = 32.dp,
                    text = "Invite Friends",
                    iconModifier = Modifier.graphicsLayer { rotationY = 180f })
            }
        }
    }

}

@Composable
private fun MenuItem(
    modifier: Modifier,
    icon: ImageVector,
    iconSize: Dp,
    text: String,
    iconModifier: Modifier = Modifier,
    isNotification: MutableState<Boolean>? = null,
    icon2: ImageVector? = null,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        CustomIcon(icon, size = iconSize, modifier = iconModifier)
        Text(text)
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            if (isNotification != null) {
                Switch(checked = isNotification.value,
                    onCheckedChange = { isNotification.value = it })
            } else if (icon2 != null){
                CustomIcon(imageVector = icon2)
            }
        }
    }
}

@Composable
private fun CustomIcon(imageVector: ImageVector, modifier: Modifier = Modifier, size: Dp = 25.dp) {
    Icon(imageVector = imageVector, contentDescription = null,
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp)
            .size(size)
            .alpha(.5f))
}