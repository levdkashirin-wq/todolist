package com.example.todoapi.dao

import com.example.todoapi.entity.Task
import com.example.todoapi.entity.TaskPriority
import com.example.todoapi.entity.TaskStatus
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.time.LocalDate

@RegisterConstructorMapper(Task::class)
interface TaskDao {

    @SqlQuery("SELECT * FROM tasks ORDER BY id")
    fun getAll(): List<Task>

    @SqlQuery("SELECT * FROM tasks WHERE id = :id")
    fun getById(@Bind("id") id: Long): Task?

    @SqlUpdate("""
        INSERT INTO tasks (title, description, status, priority, due_date)
        VALUES (:title, :description, :status, :priority, :dueDate)
    """)
    fun insert(
        @Bind("title") title: String,
        @Bind("description") description: String?,
        @Bind("status") status: TaskStatus,
        @Bind("priority") priority: TaskPriority?,
        @Bind("dueDate") dueDate: LocalDate?
    ): Long

    @SqlUpdate("""
        UPDATE tasks 
        SET title = COALESCE(:title, title),
            description = COALESCE(:description, description),
            priority = COALESCE(:priority, priority),
            due_date = COALESCE(:dueDate, due_date),
            updated_at = NOW()
        WHERE id = :id
    """)
    fun update(
        @Bind("id") id: Long,
        @Bind("title") title: String?,
        @Bind("description") description: String?,
        @Bind("priority") priority: TaskPriority?,
        @Bind("dueDate") dueDate: LocalDate?
    ): Int

    @SqlUpdate("UPDATE tasks SET status = :status, updated_at = NOW() WHERE id = :id")
    fun updateStatus(@Bind("id") id: Long, @Bind("status") status: TaskStatus): Int

    @SqlUpdate("DELETE FROM tasks WHERE id = :id")
    fun delete(@Bind("id") id: Long): Int
}