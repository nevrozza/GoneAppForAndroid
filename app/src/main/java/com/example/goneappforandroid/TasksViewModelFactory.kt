package com.example.goneappforandroid

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.goneappforandroid.data.TasksRepository

class TasksViewModelFactory(private val repository: TasksRepository, private val context: Context): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = TasksViewModel(repository, context = context) as T
}