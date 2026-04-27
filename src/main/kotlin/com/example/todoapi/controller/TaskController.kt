package com.example.todoapi.controller

import com.example.todoapi.dto.CreateTaskRequest
import com.example.todoapi.dto.UpdateStatusRequest
import com.example.todoapi.dto.UpdateTaskRequest
import com.example.todoapi.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
class TaskController(private val taskService: TaskService) {

    @GetMapping
    fun getAllTasks(): ResponseEntity<*> {
        return try {
            ResponseEntity.ok(taskService.getAllTasks())
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to e.message))
        }
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Long) = taskService.getTaskById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTask(@RequestBody request: CreateTaskRequest) = taskService.createTask(request)

    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: Long, @RequestBody request: UpdateTaskRequest) =
        taskService.updateTask(id, request)

    @PatchMapping("/{id}/status")
    fun updateStatus(@PathVariable id: Long, @RequestBody request: UpdateStatusRequest) =
        taskService.updateStatus(id, request.status)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTask(@PathVariable id: Long) = taskService.deleteTask(id)
}
