package com.example.todoapi.service

import com.example.todoapi.dao.TaskDao
import com.example.todoapi.dto.*
import com.example.todoapi.entity.Task
import com.example.todoapi.entity.TaskStatus
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class TaskService(private val taskDao: TaskDao) {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    fun getAllTasks(): List<TaskResponse> {
        return taskDao.getAll().map { toResponse(it) }
    }

    fun getTaskById(id: Long): TaskResponse {
        val task = taskDao.getById(id)
            ?: throw NoSuchElementException("Task not found with id: $id")
        return toResponse(task)
    }

    fun createTask(request: CreateTaskRequest): TaskResponse {
        if (request.title.isBlank()) {
            throw IllegalArgumentException("Task title cannot be empty")
        }

        val id = taskDao.insert(
            title = request.title,
            description = request.description,
            status = TaskStatus.PENDING,
            priority = request.priority,
            dueDate = request.dueDate
        )

        val task = taskDao.getById(id)
            ?: throw RuntimeException("Failed to create task")
        return toResponse(task)
    }

    fun updateTask(id: Long, request: UpdateTaskRequest): TaskResponse {
        val existing = taskDao.getById(id)
            ?: throw NoSuchElementException("Task not found with id: $id")

        taskDao.update(
            id = id,
            title = request.title ?: existing.title,
            description = request.description ?: existing.description,
            priority = request.priority ?: existing.priority,
            dueDate = request.dueDate ?: existing.dueDate
        )

        val updated = taskDao.getById(id)
            ?: throw RuntimeException("Failed to update task")
        return toResponse(updated)
    }

    fun updateStatus(id: Long, status: TaskStatus): TaskResponse {
        val existing = taskDao.getById(id)
            ?: throw NoSuchElementException("Task not found with id: $id")

        taskDao.updateStatus(id, status)

        val updated = taskDao.getById(id)
            ?: throw RuntimeException("Failed to update status")
        return toResponse(updated)
    }

    fun deleteTask(id: Long) {
        val existing = taskDao.getById(id)
            ?: throw NoSuchElementException("Task not found with id: $id")
        taskDao.delete(id)
    }

    private fun toResponse(task: Task): TaskResponse {
        return TaskResponse(
            id = task.id,
            title = task.title,
            description = task.description,
            status = task.status,
            priority = task.priority,
            dueDate = task.dueDate,
            createdAt = task.createdAt.format(formatter),
            updatedAt = task.updatedAt.format(formatter)
        )
    }
}