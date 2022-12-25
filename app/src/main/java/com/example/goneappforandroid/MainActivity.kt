package com.example.goneappforandroid

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.State
import com.example.goneappforandroid.compose.BaseScreen
import com.example.goneappforandroid.data.Task
import com.example.goneappforandroid.data.TasksDatabase
import com.example.goneappforandroid.data.TasksRepositoryImpl
import kotlinx.parcelize.RawValue
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = TasksDatabase.getInstance(context = application).dao
        val repository = TasksRepositoryImpl(dao)
        val factory = TasksViewModelFactory(repository)

        createNotificationChannel()

        setContent {
            BaseScreen(factory = factory, context = this, alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager)
        }

    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "DoneAppReminderChannel"
            val descript = "Channel for Alarm Manager's notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("DoneApp", name, importance).apply {
                description = descript
            }
            val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }

    }
}
//notificationManager = getSystemService(NotificationManger::class.java)


@kotlinx.parcelize.Parcelize
data class Parcelize(
    val tasksList: @RawValue State<List<Task>>
    ) : Parcelable

fun setRepeatAlarm(alarmManager: AlarmManager, context: Context, parcelize: Parcelize, ) {
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
    intent.putExtra("parcelize", parcelize)
    alarmManager.setRepeating(
        AlarmManager.RTC,
        Calendar.getInstance().timeInMillis,
        AlarmManager.INTERVAL_FIFTEEN_MINUTES,
        pendingIntent
    )
}

fun cancelAlarm(alarmManager: AlarmManager, context: Context) {
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
    alarmManager.cancel(pendingIntent)
}
