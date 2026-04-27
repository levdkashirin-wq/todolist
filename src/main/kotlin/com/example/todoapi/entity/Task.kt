package com.example.todoapi.entity

import java.time.LocalDate
import java.time.LocalDateTime

enum class TaskStatus {
    PENDING,
    IN_PROGRESS,
    DONE
}

enum class TaskPriority {
    HIGH,
    MEDIUM,
    LOW
}

data class Task(
    val id: Long,
    val title: String,
    val description: String?,
    val status: TaskStatus,
    val priority: TaskPriority?,
    val dueDate: LocalDate?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)