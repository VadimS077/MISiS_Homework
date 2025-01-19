package com.example.misis.UI.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.misis.Core.ToDoItem.TaskImportance
import com.example.misis.Core.ToDoItem.TodoItem
import com.example.misis.Core.ToDoItem.mapTaskImportanceToText
import java.time.OffsetDateTime

@Composable
fun TaskScreen(
    onTaskAdd: (TodoItem) -> Unit,
    onCancel: () -> Unit,
    initialTask: TodoItem? = null,
    onUpdate: (TodoItem) -> Unit
) {

    var taskText by remember { mutableStateOf(initialTask?.bodyText ?: "") }
    var selectedPriority by remember { mutableStateOf(initialTask?.importance ?: TaskImportance.DEFAULT) }
    var deadline by remember { mutableStateOf(initialTask?.deadline) }
    var expanded by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    val tempItem = TodoItem(
        id = initialTask?.id ?: System.currentTimeMillis().toString(),
        importance = selectedPriority,
        deadline = deadline,
        bodyText = "",
        isDone = false,
        creationTime = initialTask?.creationTime ?: OffsetDateTime.now(),
        lastEditTime = OffsetDateTime.now()
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            HeaderSection(
                onCancel = onCancel,
                onSave = {
                    if (taskText.isBlank()) {
                        isError = true
                    } else {
                        isError = false
                        tempItem.bodyText = taskText
                        tempItem.deadline = deadline
                        if (initialTask == null) {
                            onTaskAdd(tempItem)
                        } else {
                            onUpdate(tempItem)
                        }
                    }
                },
                isEditMode = initialTask != null
            )

            TaskDescriptionInput(
                taskText = taskText,
                onTaskTextChange = { taskText = it },
                isError = isError
            )

            PrioritySelector(
                selectedPriority = selectedPriority,
                onPrioritySelected = { selectedPriority = it },
                expanded = expanded,
                onExpandedChange = { expanded = it }
            )

            DeadlinePickerSection(
                deadline = deadline,
                onDateSelected = { deadline = it }
            )
        }
    }
}

@Composable
private fun HeaderSection(
    onCancel: () -> Unit,
    onSave: () -> Unit,
    isEditMode: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, top = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onCancel) {
            Icon(
                imageVector = Icons.Default.Close,
                tint = MaterialTheme.colorScheme.error,
                contentDescription = "Cancel"
            )
        }

        Text(
            text = if (isEditMode) "Редактирование задачи" else "Добавление задачи",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.displayMedium
        )

        IconButton(onClick = onSave) {
            Icon(
                imageVector = Icons.Default.Check,
                tint = MaterialTheme.colorScheme.surfaceVariant,
                contentDescription = "Save"
            )
        }
    }
}

@Composable
private fun TaskDescriptionInput(
    taskText: String,
    onTaskTextChange: (String) -> Unit,
    isError: Boolean
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .fillMaxSize()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(5.dp)
            )
    ) {
        TextField(
            value = taskText,
            onValueChange = onTaskTextChange,
            modifier = Modifier.fillMaxSize(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedTextColor = MaterialTheme.colorScheme.onSecondary
            ),
            label = { Text(text = "Описание", modifier = Modifier.fillMaxWidth()) },
            isError = isError
        )

        if (taskText.isEmpty()) {
            Text(
                text = "Описание",
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)),
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp)
            )
        }
    }

    if (isError) {
        Text(
            text = "Описание не может быть пустым",
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrioritySelector(
    selectedPriority: TaskImportance,
    onPrioritySelected: (TaskImportance) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    Text(text = "Важность:", color = MaterialTheme.colorScheme.onSecondary)
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        OutlinedTextField(
            value = mapTaskImportanceToText(selectedPriority),
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryEditable),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                focusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .border(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant),
            onDismissRequest = { onExpandedChange(false) }
        ) {
            TaskImportance.entries.forEach { priority ->
                DropdownMenuItem(
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.primary),
                    text = {
                        Text(
                            text = mapTaskImportanceToText(priority),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.displayMedium
                        )
                    },
                    onClick = {
                        onPrioritySelected(priority)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}

@Composable
private fun DeadlinePickerSection(
    deadline: OffsetDateTime?,
    onDateSelected: (OffsetDateTime?) -> Unit
) {
    Text(text = "Сделать до:", color = MaterialTheme.colorScheme.onSecondary)
    Box(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            DeadlinePicker(
                initialDate = deadline,
                onDateSelected = onDateSelected
            )
        }
    }
}
