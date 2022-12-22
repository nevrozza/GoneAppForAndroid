package com.example.goneappforandroid.data

import kotlinx.coroutines.flow.Flow

class TasksRepositoryImpl(private val dao: TasksDAO): TasksRepository {

    override suspend fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    override suspend fun updateTask(id: Int, checked: Boolean) {
        dao.checkTask(id, checked)
    }

    override suspend fun deleteTask(id: Int) {
        dao.deleteTask(id)
    }

    override fun getTasks(): Flow<List<Task>> = dao.getTasks()
}