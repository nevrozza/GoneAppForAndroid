package com.example.goneappforandroid.compose.tasks.tutorial

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.goneappforandroid.TasksViewModel
import com.example.goneappforandroid.compose.bottomappbar.CustomBottomAppBarItems
import com.example.goneappforandroid.compose.prefFirstStart
import com.example.goneappforandroid.compose.settings.MenuItem
import com.example.goneappforandroid.compose.tasks.Task

@Composable
fun TutorialScreen(
    tasksViewModel: TasksViewModel,
    confettiGo: MutableState<Boolean>,
    navHostController: NavHostController,
    local: Context
) {
    val text = listOf("1", "2", "3")
    Column(modifier = Modifier.fillMaxSize()) {
        for (i in text.indices) {
            Task(
                text = text[i],
                id = i,
                tasksViewModel = tasksViewModel,
                minute = 0,
                hour = 0,
                day = 999999999999999999,
                checked = false,
                confettiGo = confettiGo)
        }
        var currentState = remember { mutableStateOf(MutableTransitionState(false)) }
        currentState.value.targetState = true
        AnimatedVisibility(visibleState = currentState.value,
            enter = fadeIn(tween(800))) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier
                .height(70.dp)
                .clickable(indication = null, interactionSource = MutableInteractionSource()) { }) {
                MenuItem(icon = Icons.Rounded.ArrowForwardIos,
                    text = "Get Started",
                    size = 25.dp,
                    textColor = MaterialTheme.colorScheme.primary,
                    iconColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        prefFirstStart(local)
                        navHostController.navigate(CustomBottomAppBarItems.tasks.route)
                    })
            }
        }
    }
}