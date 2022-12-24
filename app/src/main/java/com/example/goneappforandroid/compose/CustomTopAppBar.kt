@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)

package com.example.goneappforandroid.compose

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun CustomTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    titleState: MutableState<String>,
    navHostController: NavHostController
) {
    AnimatedContent(targetState = titleState.value, transitionSpec = {customTransitionSpec()}) {title ->
        TopAppBar(title = { Text(text = title) },
            navigationIcon = {
                AnimatedContent(targetState = navHostController.currentDestination?.route, transitionSpec = {customTransitionSpec()}) { route ->
                    IconButton(onClick = {
                        if(route == "overview" || route == "history"){
                            navHostController.navigateUp()
                        }
                    }, modifier = Modifier.padding(top = 2.dp).alpha(if(route == "overview" || route == "history") 1f else 0f)) {
                        Icon(imageVector = Icons.Rounded.ArrowBackIos, contentDescription = null)
                    }
                }

            },
            scrollBehavior = scrollBehavior)
    }
}

private fun customTransitionSpec():  ContentTransform {return fadeIn(tween(800)) with fadeOut()}