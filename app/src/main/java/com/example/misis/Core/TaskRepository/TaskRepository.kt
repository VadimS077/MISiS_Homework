package com.example.misis.Core.TaskRepository

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.misis.Core.ToDoItem.TodoItem
import com.example.misis.Core.TaskStorage.TaskStorage

class TaskRepository : ViewModel() {

    private val tasks = mutableStateListOf<TodoItem>()

    fun createTask(task: TodoItem) {
        tasks.add(task)
        TaskStorage.saveTask(task)
    }

    fun getAllTasks(): List<TodoItem> {
        return tasks
    }

    fun updateTask(updatedTask: TodoItem) {
        val index = tasks.indexOfFirst { it.id == updatedTask.id }
        if (index != -1) {
            tasks[index] = updatedTask
            TaskStorage.saveTask(updatedTask)
        }
    }

    fun removeTaskById(taskId: String) {
        tasks.removeAll { it.id == taskId }
        TaskStorage.deleteTask(taskId)
    }
}