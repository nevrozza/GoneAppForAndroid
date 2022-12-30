package com.example.goneappforandroid

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.State
import com.example.goneappforandroid.compose.BaseScreen
import com.example.goneappforandroid.compose.settings.prefNotification
import com.example.goneappforandroid.data.Task
import com.example.goneappforandroid.data.TasksDatabase
import com.example.goneappforandroid.data.TasksRepositoryImpl
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = TasksDatabase.getInstance(context = application).dao
        val repository = TasksRepositoryImpl(dao)
        val factory =
            TasksViewModelFactory(repository, getSystemService(ALARM_SERVICE) as AlarmManager, this)



        setContent {
            BaseScreen(factory = factory)
        }

    }

    override fun onResume() {
        super.onResume()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "DoneAppReminderChannel"
            val descript = "Channel for Alarm Manager's notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("DoneApp", name, importance).apply {
                description = descript
            }
            val notificationManager =
                getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.cancelAll()
            notificationManager.createNotificationChannel(channel)

        }

    }
}
//notificationManager = getSystemService(NotificationManger::class.java)


@SuppressLint("UnspecifiedImmutableFlag")
fun setAlarm(alarmManager: AlarmManager, context: Context, task: Task) {
    Log.e("asd", "daw")
    if (prefNotification(context, isLoad = true)) {
        Log.e("asd", "daw22")
        var hour = task.hour - 8
        var day = task.day
        if (hour < 0) {
            day--
            hour += 24
        }
        val calendar = Calendar.getInstance()


        calendar(calendar, day, hour, task.minute)


        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("text", task.text)
        intent.putExtra("label", context.getString(R.string.notification_expiring))
        intent.putExtra("id", task.id)
        val pendingIntent = PendingIntent.getBroadcast(context, task.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmClockInfo = AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent)
        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent)
    }
}

@SuppressLint("UnspecifiedImmutableFlag")
fun setWeekAlarm(alarmManager: AlarmManager, context: Context, tasksList: List<Task>) {
    if (tasksList.isNotEmpty() && prefNotification(context, isLoad = true)) {

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 15)
        calendar.set(Calendar.MINUTE, 30)

        var checked = 0
        for (i in tasksList){
            if(i.checked) checked++
        }
        val text = "${context.getText(R.string.you_completed)} $checked (${(checked/((tasksList.size).toFloat())*100).toInt()}%) ${context.getText(R.string.of)} ${context.resources.getQuantityString(R.plurals.tasks,
            tasksList.size+2,
            tasksList.size)}"

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("text", text)
        intent.putExtra("label", context.getString(R.string.notification_overview))
        intent.putExtra("id", 0)
        val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmClockInfo = AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent)
        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent)
    }
}

private fun calendar(
    calendar: Calendar,
    day: Int,
    hour: Int = 18,
    minute: Int = 30,
) {
    val calYear = day / 365 + 1901
    val calMonth = (day - (calYear - 1901) * 365) / 30
    val calDay = (day - (calYear - 1901) * 365 - (calMonth - 1) * 30)

    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.YEAR, calYear)
    calendar.set(Calendar.MONTH, calMonth)
    calendar.set(Calendar.DAY_OF_MONTH, calDay)

}

@SuppressLint("UnspecifiedImmutableFlag")
fun cancelAlarm(alarmManager: AlarmManager, context: Context, id: Int) {
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0)
    alarmManager.cancel(pendingIntent)
}
