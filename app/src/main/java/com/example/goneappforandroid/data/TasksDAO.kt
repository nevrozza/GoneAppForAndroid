package com.example.goneappforandroid.data

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDAO {
    @Insert
    suspend fun insertTask(task: Task)

    @Query("UPDATE task_table SET checked = :checked WHERE id = :id")
    suspend fun checkTask(id: Int, checked: Boolean)

    @Query("DELETE FROM task_table WHERE id = :id")
    suspend fun deleteTask(id: Int)

    @Query("SELECT * FROM task_table")
    fun getTasks(): Flow<List<Task>>
}