package com.example.goneappforandroid

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.goneappforandroid.data.Task
import com.example.goneappforandroid.data.TasksDatabase
import com.example.goneappforandroid.data.TasksRepositoryImpl
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("yeah", "yeah")


            val parcelize = intent?.getParcelableExtra<Parcelize>("parcelize")

        val builder = NotificationCompat.Builder(context!!, "DoneApp")
            .setSmallIcon(R.drawable.icon)
            .setContentTitle("Done App")
            .setContentText("")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        val notificationManager = NotificationManagerCompat.from(context)
        if(parcelize?.tasksList != null) {
            for (i in parcelize.tasksList.value) {
                Log.e("id", i.id.toString())
            }
        } else {
            Log.e("fuck", "tasksList == null")
        }
        notificationManager.notify(123, builder.build())
    }

}