@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class)

package com.example.goneappforandroid.compose.tasks

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.goneappforandroid.R
import com.example.goneappforandroid.TasksViewModel
import com.example.goneappforandroid.data.Task

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasksScreen(
    tasksViewModel: TasksViewModel,
    tasksList: State<List<Task>>,
    confettiGo: MutableState<Boolean>
) {
    val lazyState = rememberLazyListState()
    var firstDeploy by remember { mutableStateOf(true) }


    Column(modifier = Modifier.fillMaxSize()) {

        if (tasksList.value.isEmpty()) {
            //crutch for animation when tap
            var currentState = remember { mutableStateOf(MutableTransitionState(false)) }
            currentState.value.targetState = true
            AnimatedVisibility(visibleState = currentState.value, enter = fadeIn(tween(800))) {
                Task(id = tasksList.value.size,
                    tasksViewModel = tasksViewModel, confettiGo = confettiGo)
            }
        }
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            LazyColumn(
                state = lazyState,
                modifier = Modifier
                    .fillMaxWidth()) {
                items(items = tasksList.value, key = {item -> item.id}) { item ->
                    var tweenDur = remember {
                        mutableStateOf(if(tasksList.value.last().id == item.id && !firstDeploy) 0 else 800)
                    }
                    Column(modifier = Modifier.animateItemPlacement()) {
                            Task(
                                text = item.text,
                                id = item.id,
                                minute = item.minute,
                                hour = item.hour,
                                day = item.day,
                                checked = item.checked,
                                tasksViewModel = tasksViewModel,
                                confettiGo = confettiGo, tweenDur = tweenDur)
                        tweenDur.value = 800
                        if (tasksList.value.last().id == item.id) {
                            firstDeploy = false
                            Task(tasksViewModel = tasksViewModel,
                                id = tasksList.value.size,
                                confettiGo = confettiGo)
                            Spacer(modifier = Modifier.height(300.dp))
                        }
                    }

                }
            }
        }
    }

}