@file:OptIn(DelicateCoroutinesApi::class)

package com.example.goneappforandroid

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.goneappforandroid.data.Task
import com.example.goneappforandroid.data.TasksDatabase
import com.example.goneappforandroid.data.TasksRepositoryImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("yeah", "yeah")
        val text = intent!!.getStringExtra("text")
        val label = intent.getStringExtra("label")
        val id = intent.getIntExtra("id", 123)



        val builder = NotificationCompat.Builder(context!!, "DoneApp")
            .setSmallIcon(R.drawable.icon)
            .setContentTitle(label)
            .setContentText(text)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(id, builder.build())
    }
}
