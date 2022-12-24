package com.example.goneappforandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.goneappforandroid.compose.BaseScreen
import com.example.goneappforandroid.data.TasksDatabase
import com.example.goneappforandroid.data.TasksRepositoryImpl

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = TasksDatabase.getInstance(context = application).dao
        val repository = TasksRepositoryImpl(dao)
        val factory = TasksViewModelFactory(repository)

        setContent {
            BaseScreen(factory = factory)
        }
    }
}

