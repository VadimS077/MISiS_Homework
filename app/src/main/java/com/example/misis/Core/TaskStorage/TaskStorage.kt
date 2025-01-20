package com.example.misis.Core.TaskStorage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.misis.Core.ToDoItem.TodoItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tasks_datastore")

object TaskStorage {
    private lateinit var dataStore: DataStore<Preferences>
    private val gson = Gson()
    private val tasksKey = stringPreferencesKey("Tasks")

    fun initialize(context: Context) {
        dataStore = context.dataStore
    }

    fun saveTasks(tasks: List<TodoItem>) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[tasksKey] = gson.toJson(tasks)
            }
        }
    }

    fun retrieveTasks(): List<TodoItem> {
        return runBlocking {
            val tasksJson = dataStore.data.first()[tasksKey]
            if (tasksJson.isNullOrEmpty()) {
                emptyList()
            } else {
                val taskType = object : TypeToken<List<TodoItem>>() {}.type
                gson.fromJson(tasksJson, taskType) ?: emptyList()
            }
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
