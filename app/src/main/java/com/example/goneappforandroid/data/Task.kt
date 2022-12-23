package com.example.goneappforandroid.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "minute")
    val minute: Int,
    @ColumnInfo(name = "hour")
    val hour: Int,
    @ColumnInfo(name = "day")
    val day: Long,
    @ColumnInfo(name = "checked")
    val checked: Boolean
)
