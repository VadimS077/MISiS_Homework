package com.example.misis.Core.ToDoItem


import java.time.OffsetDateTime

enum class TaskImportance {
    LOW,
    DEFAULT,
    HIGH
}

fun mapTaskImportanceToText(importance: TaskImportance): String {
    return when (importance) {
        TaskImportance.LOW -> "⬇"
        TaskImportance.DEFAULT ->  "нет"
        TaskImportance.HIGH ->  "❗❗ "
    }
}

data class TodoItem(
    val id: String,
    var bodyText: String,
    val importance: TaskImportance,
    val isDone: Boolean,
    val creationTime: OffsetDateTime,
    var deadline: OffsetDateTime?,
    val lastEditTime: OffsetDateTime?,
)