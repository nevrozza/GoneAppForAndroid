@file:OptIn(DelicateCoroutinesApi::class)

package com.example.goneappforandroid

import android.annotation.SuppressLint
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

class TasksViewModel(private val repository: TasksRepository, context: Context) : ViewModel() {

    fun insertTask(text: String, minute: Int, hour: Int, day: Long, checked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(Task(0, text, minute, hour, day, checked))
        }
    }

    fun checkTask(id: Int, checked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.checkTask(id, checked)
        }
    }

    fun textTask(id: Int, text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.textTask(id, text)
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(id)
        }
    }

    val tasks = repository.getTasks()

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context = context)
            setPeriodicNotification(context = context, tasks = tasks)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RestrictedApi")
private fun setPeriodicNotification(context: Context, tasks: Flow<List<Task>>) {
    if (notificationGo.value) {
        Log.e("asd", "sadsad")
        createNotification(context, tasks)
    }
    val workManager = WorkManager.getInstance(context)

    val notificationWorker =
        PeriodicWorkRequestBuilder<NotificationWorker>(10, TimeUnit.SECONDS)
            .addTag("TAG")
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()

    workManager.enqueueUniquePeriodicWork("TAG", ExistingPeriodicWorkPolicy.KEEP, notificationWorker)

    workManager.getWorkInfoByIdLiveData(notificationWorker.id).observeForever(androidx.lifecycle.Observer {
        Log.e("sad", "got")
        if(it.state == WorkInfo.State.SUCCEEDED){
            Log.e("sad", "gsadsadsadsadsadsadasdsa")
        }
    })



}

private fun createNotification(context: Context, tasks: Flow<List<Task>>) {
    notificationGo.value = false

    var tasksLists = listOf(listOf(Task(1, "1", 1, 1, 1, false)))
    GlobalScope.launch {
        tasksLists = tasks.toList()
    }
    val tasksList = tasksLists[0]

    val cal = mutableStateOf(Calendar.getInstance())
    var notificationContentText = ""

    var notificationId = 0
    var notificationTitle = context.resources.getString(R.string.notification_expiring)
    Log.e("dayofweek", cal.value.get(Calendar.DAY_OF_WEEK).toString())
    if (cal.value.get(Calendar.DAY_OF_WEEK) == 7) {
        notificationId = -1
        notificationTitle = context.resources.getString(R.string.notification_overview)
        notificationContentText += context.resources.getString(R.string.you_completed)

        for (i in tasksList) {
            val duration = durationReturn(i.day,
                i.minute,
                i.hour,
                cal,
                minutesText = context.resources.getString(R.string.minutes),
                hoursText = context.resources.getString(R.string.hours))

            if (duration.subSequence(0, duration.length - 2).toString().toLong() < 0) {

            }
        }


        buildBuilder(context, notificationTitle, notificationContentText, notificationId)
    } else {
        for (i in tasksList) {
            val duration = durationReturn(i.day,
                i.minute,
                i.hour,
                cal,
                minutesText = context.resources.getString(R.string.minutes),
                hoursText = context.resources.getString(R.string.hours))
            if (i.checked && duration.last()
                    .toString() == context.resources.getString(R.string.minutes) || duration.subSequence(
                    0,
                    duration.length - 2).toString()
                    .toLong() < 6 && duration.last()
                    .toString() == context.resources.getString(R.string.hours)
            ) {
                notificationId = i.id
                notificationContentText = i.text



                buildBuilder(context, notificationTitle, notificationContentText, notificationId)
            }
        }
    }
}


private fun buildBuilder(
    context: Context,
    notificationTitle: String,
    notificationContentText: String,
    notificationId: Int,
) {
    val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle(notificationTitle)
        .setContentText(notificationContentText)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
}

private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "NotificationChannelName"
        val descriptionText = "NotificationChannelDescriptionText"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
            description = descriptionText
        }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}
