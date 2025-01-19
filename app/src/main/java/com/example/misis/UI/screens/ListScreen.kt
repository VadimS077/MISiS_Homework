package com.example.misis.UI.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.example.misis.Core.ToDoItem.TodoItem
import com.example.misis.UI.common.task.TaskUI

@Composable
fun TodoListScreen(
    todoItems: List<TodoItem>,
    modifier: Modifier = Modifier,
    onAddItemClick: () -> Unit,
    onAddItem: (TodoItem) -> Unit = {},
    onComplete: (String, Boolean) -> Unit = { _, _ -> },
    onEdit: (TodoItem) -> Unit = {},
    onDelete: (String) -> Unit,
    isDarkTheme: MutableState<Boolean>
) {
    val isAddButtonRotated = rememberSaveable { mutableStateOf(false) }
    val addButtonRotation = animatedRotation(isAddButtonRotated.value, expandedAngle = 45f)

    LaunchedEffect(Unit) {
        isAddButtonRotated.value = false
    }

    Scaffold(
        modifier = modifier.background(MaterialTheme.colorScheme.background),

    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
        ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
            ) {
                TodoHeader(
                    title = "Мои дела",
                    completedCount = todoItems.count { it.isDone }
                )

                TaskList(
                    todoItems = todoItems,
                    isExpanded = true,
                    onComplete = onComplete,
                    onEdit = onEdit,
                    onDelete = onDelete
                )
            }

            AddTaskButton(
                isRotated = isAddButtonRotated.value,
                rotation = addButtonRotation,
                onClick = {
                    isAddButtonRotated.value = !isAddButtonRotated.value
                    onAddItemClick()
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun animatedRotation(isExpanded: Boolean, expandedAngle: Float = 180f): Float {
    return animateFloatAsState(
        targetValue = if (isExpanded) expandedAngle else 0f,
        animationSpec = tween(durationMillis = 300), label = ""
    ).value
}

@Composable
fun AddTaskButton(
    isRotated: Boolean,
    rotation: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurface,
        shape = CircleShape,
        modifier = modifier.size(64.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Добавить",
            modifier = Modifier
                .size(48.dp)
                .rotate(rotation)
        )
    }
}

@Composable
fun TodoHeader(
    title: String,
    completedCount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 32.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.weight(1f)
            )
        }
        Text(
            text = "Выполнено — $completedCount",
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
fun TaskList(
    todoItems: List<TodoItem>,
    isExpanded: Boolean,
    onComplete: (String, Boolean) -> Unit,
    onEdit: (TodoItem) -> Unit,
    onDelete: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        val sortedItems = todoItems.sortedByDescending { it.isDone }
        items(sortedItems, key = { it.id }) { item ->
            if (!item.isDone || isExpanded) {
                TaskUI(
                    item = item,
                    onComplete = { isDone -> onComplete(item.id, isDone) },
                    onEdit = { task -> onEdit(task) },
                    onDelete = { id -> onDelete(id) }
                )
            }
        }
    }
}
