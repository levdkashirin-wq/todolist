CREATE TABLE tasks (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
                       priority VARCHAR(20) DEFAULT 'MEDIUM',
                       due_date DATE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_tasks_status ON tasks(status);
CREATE INDEX idx_tasks_priority ON tasks(priority);
CREATE INDEX idx_tasks_created_at ON tasks(created_at);
CREATE INDEX idx_tasks_status_priority ON tasks(status, priority);
CREATE INDEX idx_tasks_due_date ON tasks(due_date);
ALTER TABLE tasks
    ADD CONSTRAINT check_status
        CHECK (status IN ('PENDING', 'IN_PROGRESS', 'DONE'));
ALTER TABLE tasks
    ADD CONSTRAINT check_priority
        CHECK (priority IN ('HIGH', 'MEDIUM', 'LOW') OR priority IS NULL);
COMMENT ON TABLE tasks IS 'Таблица задач Todo List';
COMMENT ON COLUMN tasks.id IS 'Уникальный идентификатор задачи';
COMMENT ON COLUMN tasks.title IS 'Название задачи (обязательное поле)';
COMMENT ON COLUMN tasks.description IS 'Подробное описание задачи';
COMMENT ON COLUMN tasks.status IS 'Статус: PENDING, IN_PROGRESS, DONE';
COMMENT ON COLUMN tasks.priority IS 'Приоритет: HIGH, MEDIUM, LOW (может быть NULL)';
COMMENT ON COLUMN tasks.due_date IS 'Срок выполнения задачи';
COMMENT ON COLUMN tasks.created_at IS 'Дата и время создания задачи';
COMMENT ON COLUMN tasks.updated_at IS 'Дата и время последнего обновления';