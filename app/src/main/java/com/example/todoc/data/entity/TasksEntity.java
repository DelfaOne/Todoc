package com.example.todoc.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

//Represent table in SQLite database

@Entity(tableName = "tasks_table")
public class TasksEntity {

    @PrimaryKey(autoGenerate = true)
    public long id = 0;

    public long projectId;

    public String taskName;

    public String taskCreatedAt;

    public TasksEntity(long id, long projectId, String taskName, String taskCreatedAt) {
        this.id = id;
        this.projectId = projectId;
        this.taskName = taskName;
        this.taskCreatedAt = taskCreatedAt;
    }
}
