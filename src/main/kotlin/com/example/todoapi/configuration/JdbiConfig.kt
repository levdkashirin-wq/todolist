package com.example.todoapi.configuration

import com.example.todoapi.dao.TaskDao
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class JdbiConfig {

    @Bean
    fun jdbi(dataSource: DataSource): Jdbi {
        return Jdbi.create(dataSource)
            .installPlugin(SqlObjectPlugin())
    }

    @Bean
    fun taskDao(jdbi: Jdbi): TaskDao {
        return jdbi.onDemand(TaskDao::class.java)
    }
}