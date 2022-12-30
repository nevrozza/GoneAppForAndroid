package com.example.goneappforandroid

import android.app.AlarmManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.goneappforandroid.data.TasksRepository

class TasksViewModelFactory(private val repository: TasksRepository, val alarmManager: AlarmManager, val context: Context): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = TasksViewModel(repository, alarmManager, context) as T
}