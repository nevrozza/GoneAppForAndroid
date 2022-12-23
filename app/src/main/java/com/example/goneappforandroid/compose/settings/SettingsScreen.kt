@file:OptIn(ExperimentalTextApi::class)

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
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.goneappforandroid.R

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
                size2 = 25.dp,
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
                icon = icGitHub,
                text = stringResource(id = R.string.github),
                onClick = {})

            MenuItem(
                icon = Icons.Rounded.Share,
                text = stringResource(id = R.string.share),
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
    textAdd: String? = null,
    isNotification: MutableState<Boolean>? = null,
    icon2: ImageVector? = null,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    iconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    size: Dp = 30.dp,
    onClick: () -> Unit = {},
    onPressAnimation: Boolean = true,
    iconAlpha: Float = .5f,
    isHistory: Boolean = false,
    size2: Dp = 30.dp
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
            iconColor = iconColor,
            size = size,
            iconAlpha = iconAlpha
        )

        Text(buildAnnotatedString {
            withStyle(style = if(isHistory) SpanStyle(
                textDecoration = TextDecoration.LineThrough,
                brush = SolidColor(textColor),
                alpha = .5f
            ) else SpanStyle(color = textColor)) {
                append(text)
            }
            withStyle(style = SpanStyle(
                brush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
                alpha = 0.5f,
                fontWeight = FontWeight.Normal
            )) {
                if(textAdd != null) {
                    append(" · $textAdd")
                }
            }
        })


        if(isNotification != null || icon2 != null) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                if (isNotification != null) {
                    Switch(checked = isNotification.value,
                        onCheckedChange = {
                            onClick()
                        })
                } else if (icon2 != null) {
                    CustomIcon(imageVector = icon2, iconColor = iconColor, size = size2)
                }
            }
        }
    }
}

@Composable
fun CustomIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    size: Dp = 30.dp,
    iconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    iconAlpha: Float = .5f
) {
    Icon(imageVector = imageVector, contentDescription = null,
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp)
            .size(size).alpha(iconAlpha), tint = iconColor)
}