@file:OptIn(DelicateCoroutinesApi::class)

package com.example.goneappforandroid

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.goneappforandroid.compose.settings.prefNotification
import com.example.goneappforandroid.data.Task
import com.example.goneappforandroid.data.TasksDatabase
import com.example.goneappforandroid.data.TasksRepositoryImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("yeah", "yeah")
        if (prefNotification(context!!, isLoad = true)) {
            val text = intent!!.getStringExtra("text")
            val label = intent.getStringExtra("label")
            val id = intent.getIntExtra("id", 123)
            val resultIntent = Intent(context, MainActivity::class.java)
            val resultPendingIntent = PendingIntent.getActivity(context,
                id,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
            val builder = NotificationCompat.Builder(context, "DoneApp")
                .setSmallIcon(R.drawable.icon_tab)
                .setContentTitle(label)
                .setContentText(text)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setDefaults(NotificationCompat.FLAG_AUTO_CANCEL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(resultPendingIntent)

            if(id != 0) {
                builder.setGroup("tasks")
            } else {
                builder.setGroup("overview")
            }
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(id, builder.build())
        }
    }
}
