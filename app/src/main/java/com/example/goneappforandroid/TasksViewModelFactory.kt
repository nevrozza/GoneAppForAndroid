package com.example.goneappforandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.goneappforandroid.data.TasksRepository

class TasksViewModelFactory(private val repository: TasksRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = TasksViewModel(repository) as T
}