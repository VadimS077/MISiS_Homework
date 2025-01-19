package com.example.misis.UI.common.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.misis.Core.TaskRepository.TaskRepository
import com.example.misis.UI.screens.TaskScreen
import com.example.misis.UI.screens.TodoListScreen

@Composable
fun App(viewModel: TaskRepository, isSystemInDarkTheme: MutableState<Boolean>) {
    val navigator = rememberNavController()

    NavHost(navController = navigator, startDestination = "Tasks") {
        composable("Tasks") {
            TodoListScreen(
                todoItems = viewModel.getAllTasks(),
                onAddItemClick = { navigator.navigate("AddTask") },
                onComplete = { id, isCompleted -> handleTaskCompletion(viewModel, id, isCompleted) },
                onAddItem = { newItem -> viewModel.createTask(newItem) },
                onEdit = { task -> navigator.navigate("EditTask/${task.id}") },
                onDelete = { id -> viewModel.removeTaskById(id) },
                isDarkTheme = isSystemInDarkTheme
            )
        }
        composable("AddTask") {
            TaskScreen(
                onTaskAdd = { newItem ->
                    viewModel.createTask(newItem)
                    navigator.popBackStack()
                },
                onCancel = { navigator.popBackStack() },
                onUpdate = { newItem ->
                    viewModel.updateTask(newItem)
                    navigator.popBackStack()
                }
            )
        }
        composable("EditTask/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            val task = viewModel.getAllTasks().find { it.id == taskId }
            task?.let {
                TaskScreen(
                    onTaskAdd = { updatedTask ->
                        viewModel.updateTask(updatedTask)
                        navigator.popBackStack()
                    },
                    onCancel = { navigator.popBackStack() },
                    onUpdate = { newItem ->
                        viewModel.updateTask(newItem)
                        navigator.popBackStack()
                    },
                    initialTask = it
                )
            }
        }
    }
}

private fun handleTaskCompletion(viewModel: TaskRepository, id: String, isCompleted: Boolean) {
    val item = viewModel.getAllTasks().find { it.id == id }
    item?.let {
        val updatedItem = it.copy(isDone = isCompleted)
        viewModel.updateTask(updatedItem)
    }
}
