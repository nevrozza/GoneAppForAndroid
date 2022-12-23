

package com.example.goneappforandroid.compose.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.goneappforandroid.compose.tasks.durationReturn
import com.example.goneappforandroid.data.Task
import java.util.*

@Composable
fun HistoryScreen(
    tasksList: State<List<Task>>,
    cal: MutableState<Calendar>
) {
    val currentState = remember { MutableTransitionState(false) }
    currentState.targetState = true
    AnimatedVisibility(visibleState = currentState, enter = fadeIn(tween(800))) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = tasksList.value) { item ->

                val duration = durationReturn(day = item.day, minute = item.minute, hour = item.hour, cal = cal)
                if (duration.subSequence(0, duration.length - 2).toString().toLong() < 0) {
                    MenuItem(icon = Icons.Rounded.Schedule, text = item.text, isHistory = true, onPressAnimation = false)
                }
            }
        }
    }
}