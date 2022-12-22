package com.example.goneappforandroid.data


import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    suspend fun insertTask(task: Task)

    suspend fun checkTask(id: Int, checked: Boolean)

    suspend fun textTask(id: Int, text: String)

    suspend fun deleteTask(id: Int)

    fun getTasks(): Flow<List<Task>>
}