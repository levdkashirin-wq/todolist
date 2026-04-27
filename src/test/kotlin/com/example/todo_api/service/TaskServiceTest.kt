package com.example.todoapi.service

import com.example.todoapi.dao.TaskDao
import com.example.todoapi.dto.CreateTaskRequest
import com.example.todoapi.entity.Task
import com.example.todoapi.entity.TaskStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.time.LocalDateTime

class TaskServiceSimpleTest {

    private lateinit var taskDao: TaskDao
    private lateinit var taskService: TaskService

    @BeforeEach
    fun setUp() {
        taskDao = mock(TaskDao::class.java)
        taskService = TaskService(taskDao)
    }

    @Test
    fun testGetAllTasksReturnsList() {
        val task = Task(1, "Test", null, TaskStatus.PENDING, null, null, LocalDateTime.now(), LocalDateTime.now())
        `when`(taskDao.getAll()).thenReturn(listOf(task))

        val result = taskService.getAllTasks()

        assertEquals(1, result.size)
        verify(taskDao).getAll()
    }

    @Test
    fun testCreateTaskThrowsExceptionWhenTitleIsEmpty() {
        val request = CreateTaskRequest(title = "")

        try {
            taskService.createTask(request)
        } catch (e: IllegalArgumentException) {
            assertEquals("Task title cannot be empty", e.message)
        }
    }
        )
    }
}
