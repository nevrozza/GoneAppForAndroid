package com.example.goneappforandroid.compose.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.goneappforandroid.R
import com.example.goneappforandroid.compose.tasks.durationReturn
import com.example.goneappforandroid.data.Task
import com.example.goneappforandroid.ui.theme.Typography
import com.example.goneappforandroid.ui.theme.redInk
import java.util.*

@Composable
fun HistoryScreen(
    tasksList: State<List<Task>>,
    cal: MutableState<Calendar>,
) {


    var history by remember { mutableStateOf(0) }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = tasksList.value) { item ->
            val duration = durationReturn(day = item.day,
                minute = item.minute,
                hour = item.hour,
                cal = cal,
                minutesText = stringResource(id = R.string.minutes),
                hoursText = stringResource(id = R.string.hours))
            if (duration.subSequence(0, duration.length - 2).toString().toLong() <= 0) {
                history++
                MenuItem(icon = Icons.Rounded.Schedule,
                    text = item.text,
                    textColor = if (item.checked) MaterialTheme.colorScheme.primary else redInk,
                    iconColor = if (item.checked) MaterialTheme.colorScheme.primary else redInk,
                    onPressAnimation = false,
                    isHistory = true,
                    size2 = 20.dp)

            }
        }
    }
    if (history == 0) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(imageVector = icClock, contentDescription = null, modifier = Modifier
                .size(500.dp)
                .alpha(.1f), tint = Color.Gray)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(stringResource(id = R.string.here_is_empty), style = Typography.titleLarge)
                Text(stringResource(id = R.string.fall_here), modifier = Modifier.alpha(.5f))
            }
        }
    }

}