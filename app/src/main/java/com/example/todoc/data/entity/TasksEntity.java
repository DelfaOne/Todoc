package com.example.todoc.data.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//Represent table in SQLite database

@Entity(tableName = "tasks_table")
public class TasksEntity {

    @PrimaryKey(autoGenerate = true)
    private final long id;

    private final long projectId;

    private final String taskName;

    private final String taskCreatedAt;

    public TasksEntity(long id, long projectId, String taskName, String taskCreatedAt) {
        this.id = id;
        this.projectId = projectId;
        this.taskName = taskName;
        this.taskCreatedAt = taskCreatedAt;
    }

    @Ignore
    public TasksEntity(long projectId, String taskName, String taskCreatedAt) {
        this.id = 0;
        this.projectId = projectId;
        this.taskName = taskName;
        this.taskCreatedAt = taskCreatedAt;
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

    public String getTaskCreatedAt() {
        return taskCreatedAt;
    }
}
