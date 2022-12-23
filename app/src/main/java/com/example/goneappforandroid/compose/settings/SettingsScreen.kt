package com.example.goneappforandroid.compose.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.goneappforandroid.R
import com.example.goneappforandroid.ui.theme.Typography

@Composable
fun SettingsScreen(navHostController: NavHostController) {
    val currentState = remember { MutableTransitionState(false) }
    currentState.targetState = true
    val local = LocalContext.current
    val isNotification = remember {
        mutableStateOf(prefNotification(local, isLoad = true))
    }
    AnimatedVisibility(visibleState = currentState, enter = fadeIn(tween(800))) {
        Column(modifier = Modifier.fillMaxSize()) {
            MenuItem(
                icon = Icons.Rounded.TaskAlt,
                text = stringResource(id = R.string.weekly_overview),
                icon2 = Icons.Rounded.ArrowForwardIos,
                onClick = {
                    navHostController.navigate("overview")
                })


            MenuItem(endPadding = 20.dp,
                icon = Icons.Outlined.Notifications,
                text = stringResource(id = R.string.notifications),
                isNotification = isNotification,
                onClick = {
                    isNotification.value = !isNotification.value
                    prefNotification(local, isNotification.value)
                })


            MenuItem(
                icon = Icons.Outlined.PersonAdd,
                text = stringResource(id = R.string.invite),
                rotation = 180f,
                onClick = {})

        }
    }

}

fun prefNotification(
    local: Context,
    value: Boolean = false,
    isLoad: Boolean = false,
): Boolean {
    val sPref = PreferenceManager.getDefaultSharedPreferences(local)
    return if (isLoad) {
        sPref.getBoolean("isNotification", true)
    } else {
        val ed: SharedPreferences.Editor = sPref.edit()
        ed.putBoolean("isNotification", value)
        ed.apply()
        true
    }
}

@Composable
fun MenuItem(
    endPadding: Dp = 10.dp,
    icon: ImageVector,
    text: String,
    rotation: Float = 0f,
    isNotification: MutableState<Boolean>? = null,
    icon2: ImageVector? = null,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    iconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    size: Dp = 30.dp,
    onClick: () -> Unit = {},
    onPressAnimation: Boolean = true
) {
    var pressed by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(70.dp)
            .padding(start = 10.dp, end = endPadding)
            .fillMaxWidth()
            .scale(animateFloatAsState(targetValue = if (pressed) 0.99f else 1f, tween(150)).value)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        if(onPressAnimation) {
                            pressed = true
                            tryAwaitRelease()
                            pressed = false
                        }
                    },
                    onTap = {
                        if(onPressAnimation){
                            pressed = true
                        }
                        onClick()
                    }
                )
            }) {
        CustomIcon(
            icon,
            modifier = Modifier.graphicsLayer { rotationY = rotation },
            iconColor = iconColor,
            size = size,
        )
        Text(text, style = Typography.titleMedium, color = textColor)
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            if (isNotification != null) {
                Switch(checked = isNotification.value,
                    onCheckedChange = {
                        onClick()
                    })
            } else if (icon2 != null) {
                CustomIcon(imageVector = icon2, iconColor = iconColor, size = size)
            }
        }
    }
}

@Composable
private fun CustomIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    size: Dp,
    iconColor: Color,
) {
    Icon(imageVector = imageVector, contentDescription = null,
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp)
            .size(size), tint = iconColor)
}