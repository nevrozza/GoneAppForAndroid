package com.example.goneappforandroid.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TasksRepositoryImpl(private val dao: TasksDAO): TasksRepository {

    override suspend fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    override suspend fun checkTask(id: Int, checked: Boolean) {
        dao.checkTask(id, checked)
    }

    override suspend fun textTask(id: Int, text: String) {
        dao.textTask(id, text)
    }

    override suspend fun deleteTask(id: Int) {
        dao.deleteTask(id)
    }

    override fun getTasks(): Flow<List<Task>> = dao.getTasks()
}