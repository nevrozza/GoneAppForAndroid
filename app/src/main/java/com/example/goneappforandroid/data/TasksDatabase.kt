package com.example.goneappforandroid.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Task::class], version = 1, exportSchema = true)
abstract class TasksDatabase: RoomDatabase() {
    abstract val dao: TasksDAO

    companion object{
        @Volatile
        private var INSTANCE: TasksDatabase? = null
        fun getInstance(context: Context): TasksDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TasksDatabase::class.java,
                        "tasks_database1").build()
                }
                return instance
            }
        }
    }
}