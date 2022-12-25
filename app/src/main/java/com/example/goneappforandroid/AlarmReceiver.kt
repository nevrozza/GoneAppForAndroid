@file:OptIn(DelicateCoroutinesApi::class)

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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("yeah", "yeah")

        val dao = TasksDatabase.getInstance(context = context!!.applicationContext).dao
        //val repository = TasksRepositoryImpl(dao)
        var list: List<Task> = listOf()
        runBlocking {
            launch {
                Log.e("sad", "dsa")
                list = dao.getTasks().flattenToList()
                Log.e("list", list.toString())
            }
        }



        val builder = NotificationCompat.Builder(context, "DoneApp")
            .setSmallIcon(R.drawable.icon)
            .setContentTitle("Done App")
            .setContentText("")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        val notificationManager = NotificationManagerCompat.from(context)

        if(list.isNotEmpty()){
            for(i in list){
                Log.e("string", i.toString())
            }
        } else {
            Log.e("log---", "---------------------------------")
        }

        notificationManager.notify(123, builder.build())
    }
    suspend fun <T> Flow<List<T>>.flattenToList() = flatMapConcat { it.asFlow() }.toList()
}
