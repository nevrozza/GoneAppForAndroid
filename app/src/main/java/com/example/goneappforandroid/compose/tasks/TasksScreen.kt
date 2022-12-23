@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.example.goneappforandroid.compose.tasks

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.goneappforandroid.TasksViewModel
import com.example.goneappforandroid.data.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasksScreen(
    tasksViewModel: TasksViewModel,
    tasksList: State<List<Task>>,
    confettiGo: MutableState<Boolean>,
    lazyState: LazyListState,
    cal: MutableState<Calendar>
) {

    var firstDeploy by remember { mutableStateOf(true) }
    val coroutine = rememberCoroutineScope()


    coroutine.launch {
        while (true) {
            delay(5000)
            cal.value = Calendar.getInstance()
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {

        if (tasksList.value.isEmpty()) {
                Task(id = tasksList.value.size,
                    tasksViewModel = tasksViewModel, confettiGo = confettiGo, cal = cal)
        }
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {

            LazyColumn(
                state = lazyState,
                modifier = Modifier
                    .fillMaxWidth()) {
                items(items = tasksList.value, key = {item -> item.id}) { item ->
                    val tweenDur = remember {
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
                                confettiGo = confettiGo, tweenDur = tweenDur,
                                cal = cal)
                        tweenDur.value = 800
                        if (tasksList.value.last().id == item.id) {
                            firstDeploy = false
                            Task(tasksViewModel = tasksViewModel,
                                id = tasksList.value.size,
                                confettiGo = confettiGo, cal = cal)
                            Spacer(modifier = Modifier.height(300.dp))
                        }
                    }

                }
            }
        }
    }

}