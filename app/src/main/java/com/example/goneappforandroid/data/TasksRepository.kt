package com.example.goneappforandroid.data


import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    suspend fun insertTask(task: Task)

    suspend fun updateTask(id: Int, checked: Boolean)

    suspend fun deleteTask(id: Int)

    fun getTasks(): Flow<List<Task>>
}