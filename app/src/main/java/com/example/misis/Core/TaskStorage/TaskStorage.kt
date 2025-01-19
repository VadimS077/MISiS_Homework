package com.example.misis.Core.TaskStorage

import android.content.SharedPreferences
import com.example.misis.Core.ToDoItem.TodoItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TaskStorage {
    private var sharedPreferences: SharedPreferences? = null
    private val gson = Gson()

    fun initialize(preferences: SharedPreferences) {
        sharedPreferences = preferences
    }

    fun saveTasks(tasks: List<TodoItem>) {
        sharedPreferences?.edit()?.putString("Tasks", gson.toJson(tasks))?.apply()
    }

    fun retrieveTasks(): List<TodoItem> {
        val tasksJson = sharedPreferences?.getString("Tasks", null)
        return if (tasksJson.isNullOrEmpty()) {
            emptyList()
        } else {
            val taskType = object : TypeToken<List<TodoItem>>() {}.type
            gson.fromJson(tasksJson, taskType) ?: emptyList()
        }
    }

    fun saveTask(task: TodoItem) {
        val tasks = retrieveTasks().toMutableList()
        val existingTaskIndex = tasks.indexOfFirst { it.id == task.id }

        if (existingTaskIndex != -1) {
            tasks[existingTaskIndex] = task
        } else {
            tasks.add(task)
        }

        saveTasks(tasks)
    }

    fun deleteTask(taskId: String) {
        val updatedTasks = retrieveTasks().filter { it.id != taskId }
        saveTasks(updatedTasks)
    }
}
