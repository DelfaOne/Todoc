package com.example.todoc.data.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Objects;

//Represent table in SQLite database

@Entity(tableName = "tasks_table")
public class TasksEntity {

    @PrimaryKey(autoGenerate = true)
    private final long id;

    private final long projectId;

    private final String taskName;

    private final LocalDateTime taskCreatedAt;

    public TasksEntity(long id, long projectId, String taskName, LocalDateTime taskCreatedAt) {
        this.id = id;
        this.projectId = projectId;
        this.taskName = taskName;
        this.taskCreatedAt = taskCreatedAt;
    }

    @Ignore
    public TasksEntity(long projectId, String taskName, LocalDateTime taskCreatedAt) {
        this.id = 0;
        this.projectId = projectId;
        this.taskName = taskName;
        this.taskCreatedAt = taskCreatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TasksEntity that = (TasksEntity) o;
        return id == that.id &&
                projectId == that.projectId &&
                Objects.equals(taskName, that.taskName) &&
                Objects.equals(taskCreatedAt, that.taskCreatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, taskName, taskCreatedAt);
    }

    public long getId() {
        return id;
    }

    public long getProjectId() {
        return projectId;
    }

    public String getTaskName() {
        return taskName;
    }

    public LocalDateTime getTaskCreatedAt() {
        return taskCreatedAt;
    }

    @Override
    public String toString() {
        return "TasksEntity{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", taskName='" + taskName + '\'' +
                ", taskCreatedAt='" + taskCreatedAt + '\'' +
                '}';
    }
}
