@file:OptIn(DelicateCoroutinesApi::class)

package com.example.goneappforandroid

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.goneappforandroid.compose.tasks.durationReturn
import com.example.goneappforandroid.data.Task
import com.example.goneappforandroid.data.TasksRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import java.time.Duration
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class TasksViewModel(private val repository: TasksRepository, private val alarmManager: AlarmManager, private val context: Context) : ViewModel() {

    fun insertTask(text: String, minute: Int, hour: Int, day: Int, checked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val task = Task(0, text, minute, hour, day, checked)
            repository.insertTask(task)

            setAlarm(alarmManager = alarmManager, context, task)
        }
    }

    fun checkTask(id: Int, checked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.checkTask(id, checked)
            cancelAlarm(alarmManager, context, id)
        }
    }

    fun textTask(id: Int, task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.textTask(id, task.text)

            setAlarm(alarmManager, context, task)
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(id)
            cancelAlarm(alarmManager, context, id)
        }
    }

    val tasks = repository.getTasks()
}

