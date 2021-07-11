package com.example.todoc.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

//Represent table in SQLite database

@Entity(tableName = "tasks_table")
public class TasksEntity {

    @PrimaryKey(autoGenerate = true)
    public final long id;

    public final long projectId;

    public final String taskName;

    public final String taskCreatedAt;

    @Deprecated //Only for ROOM Database
    public TasksEntity(long id, long projectId, String taskName, String taskCreatedAt) {
        this.id = id;
        this.projectId = projectId;
        this.taskName = taskName;
        this.taskCreatedAt = taskCreatedAt;
    }

    public TasksEntity(long projectId, String taskName, String taskCreatedAt) {
        this.id = 0;
        this.projectId = projectId;
        this.taskName = taskName;
        this.taskCreatedAt = taskCreatedAt;
    }
}
