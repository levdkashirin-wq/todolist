package com.example.todoapi.dto

import com.example.todoapi.entity.TaskPriority
import com.example.todoapi.entity.TaskStatus
import java.time.LocalDate

data class CreateTaskRequest(
    val title: String,
    val description: String? = null,
    val priority: TaskPriority? = null,
    val dueDate: LocalDate? = null
)

data class UpdateTaskRequest(
    val title: String? = null,
    val description: String? = null,
    val priority: TaskPriority? = null,
    val dueDate: LocalDate? = null
)

data class UpdateStatusRequest(
    val status: TaskStatus
)

data class TaskResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val status: TaskStatus,
    val priority: TaskPriority?,
    val dueDate: LocalDate?,
    val createdAt: String,
    val updatedAt: String
)