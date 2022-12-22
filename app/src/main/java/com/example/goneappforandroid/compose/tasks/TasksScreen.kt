@file:OptIn(ExperimentalFoundationApi::class)

package com.example.goneappforandroid.compose.tasks

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.goneappforandroid.TasksViewModel
import com.example.goneappforandroid.data.Task

@Composable
fun TasksScreen(
    tasksViewModel: TasksViewModel,
    tasksList: State<List<Task>>,
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
                    tasksViewModel = tasksViewModel, currentState = currentState)
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
                    //crutch for animation when tap
                    var currentState = remember { mutableStateOf(MutableTransitionState(false)) }
                    currentState.value.targetState = true
                    var tweenDur by remember {
                        Log.e("gasdsadsa", "${!firstDeploy}")
                        mutableStateOf(if(tasksList.value.last().id == item.id && !firstDeploy) 0 else 800)
                    }
                    AnimatedVisibility(visibleState = currentState.value,
                        enter = fadeIn(tween(tweenDur))) {
                        Task(
                            text = item.text,
                            id = item.id,
                            minute = item.minute,
                            hour = item.hour,
                            day = item.day,
                            checked = item.checked,
                            tasksViewModel = tasksViewModel, currentState = currentState)
                        tweenDur = 800
                    }
                    if (tasksList.value.last().id == item.id) {
                        firstDeploy = false
                        var currentState1 = remember { MutableTransitionState(false) }
                        currentState1.targetState = true
                        AnimatedVisibility(visibleState = currentState1,
                            enter = fadeIn(tween(800))) {
                            Task(tasksViewModel = tasksViewModel,
                                id = tasksList.value.size,
                                currentState = currentState)
                            Spacer(modifier = Modifier.height(300.dp))
                        }
                    }

                }
            }
        }
    }

}