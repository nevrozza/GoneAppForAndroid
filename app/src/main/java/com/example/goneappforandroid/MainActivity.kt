package com.example.goneappforandroid

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.goneappforandroid.compose.BaseScreen
import com.example.goneappforandroid.data.TasksDatabase
import com.example.goneappforandroid.data.TasksRepositoryImpl
import java.time.Duration
import java.util.Calendar
import kotlin.time.Duration.Companion.minutes

var notificationGo = mutableStateOf(false)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = TasksDatabase.getInstance(context = application).dao
        val repository = TasksRepositoryImpl(dao)
        val factory = TasksViewModelFactory(repository, applicationContext)

        setContent {
            BaseScreen(factory = factory)
        }
    }
}

