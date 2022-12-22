@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)

package com.example.goneappforandroid.compose

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun CustomTopAppBar(
    isLeftIcon: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    titleState: MutableState<String>
) {
    AnimatedContent(targetState = titleState.value, transitionSpec = {
        fadeIn(animationSpec = tween(400, delayMillis = 90)) +
                scaleIn(initialScale = 0.92f, animationSpec = tween(400, delayMillis = 90)) with
                fadeOut(animationSpec = tween(200))
    }) {title ->
        TopAppBar(title = { Text(text = title) },
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.alpha(if(isLeftIcon) 1f else 0f)) {
                    Icon(imageVector = Icons.Rounded.ArrowBackIos, contentDescription = null)
                }
            },
            scrollBehavior = scrollBehavior)
    }
}