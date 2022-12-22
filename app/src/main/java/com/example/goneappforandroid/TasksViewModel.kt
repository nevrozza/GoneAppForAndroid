package com.example.goneappforandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goneappforandroid.data.Task
import com.example.goneappforandroid.data.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TasksViewModel(private val repository: TasksRepository): ViewModel() {

    fun insertTask(text: String, minute: Int, hour: Int, day: Int, checked: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(Task(0, text, minute, hour, day, checked))
        }
    }

    fun checkTask(id: Int, checked: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            repository.checkTask(id, checked)
        }
    }
    fun textTask(id: Int, text: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.textTask(id, text)
        }
    }

    fun deleteTask(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(id)
        }
    }

    val tasks = repository.getTasks()
}