package com.example.todoapi.service

import com.example.todoapi.dao.TaskDao
import com.example.todoapi.dto.CreateTaskRequest
import com.example.todoapi.entity.Task
import com.example.todoapi.entity.TaskPriority
import com.example.todoapi.entity.TaskStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.time.LocalDate
import java.time.LocalDateTime

class TaskServiceTest {

    private lateinit var taskDao: TaskDao
    private lateinit var taskService: TaskService

    @BeforeEach
    fun setUp() {
        taskDao = mock(TaskDao::class.java)
        taskService = TaskService(taskDao)
    }

    @Test
    fun `getAllTasks должен вернуть список задач`() {
        val expectedTasks = listOf(
            createTestTask(1, "Задача 1"),
            createTestTask(2, "Задача 2")
        )
        `when`(taskDao.getAll()).thenReturn(expectedTasks)

        val result = taskService.getAllTasks()

        assertEquals(2, result.size)
        verify(taskDao, times(1)).getAll()
    }

    @Test
    fun `getTaskById должен вернуть задачу если она существует`() {
        val expectedTask = createTestTask(1, "Существующая задача")
        `when`(taskDao.getById(1)).thenReturn(expectedTask)

        val result = taskService.getTaskById(1)

        assertEquals("Существующая задача", result.title)
        verify(taskDao, times(1)).getById(1)
    }

    @Test
    fun `getTaskById должен выбросить исключение если задача не найдена`() {
        `when`(taskDao.getById(99)).thenReturn(null)

        assertThrows<NoSuchElementException> {
            taskService.getTaskById(99)
        }
    }

    @Test
    fun `createTask должен выбросить исключение если заголовок пустой`() {
        val request = CreateTaskRequest(title = "")

        assertThrows<IllegalArgumentException> {
            taskService.createTask(request)
        }

        verify(taskDao, never()).insert(any(), any(), any(), any(), any())
    }

    @Test
    fun `createTask должен создать задачу с корректными данными`() {
        val request = CreateTaskRequest(
            title = "Новая задача",
            description = "Описание",
            priority = TaskPriority.HIGH,
            dueDate = LocalDate.of(2026, 5, 1)
        )

        `when`(taskDao.insert(any(), any(), any(), any(), any())).thenReturn(1L)

        val createdTask = createTestTask(1, "Новая задача")
        `when`(taskDao.getById(1)).thenReturn(createdTask)

        val result = taskService.createTask(request)

        assertEquals("Новая задача", result.title)
        verify(taskDao, times(1)).insert(any(), any(), any(), any(), any())
    }

    @Test
    fun `deleteTask должен удалить задачу`() {
        val existingTask = createTestTask(1, "Задача для удаления")
        `when`(taskDao.getById(1)).thenReturn(existingTask)
        `when`(taskDao.delete(1)).thenReturn(1)

        taskService.deleteTask(1)

        verify(taskDao, times(1)).delete(1)
    }

    private fun createTestTask(id: Long, title: String): Task {
        return Task(
            id = id,
            title = title,
            description = "Тестовое описание",
            status = TaskStatus.PENDING,
            priority = TaskPriority.MEDIUM,
            dueDate = LocalDate.of(2026, 5, 1),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }
}