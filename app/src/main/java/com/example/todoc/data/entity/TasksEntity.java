package com.example.todoc.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

//Represent table in SQLite database

@Entity(tableName = "tasks_table")
public class TasksEntity {

    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    public int projectId;

    public String taskName;


    public String taskCreatedAt;

    public TasksEntity(int id, int projectId, String taskName, String taskCreatedAt) {
        this.id = id;
        this.projectId = projectId;
        this.taskName = taskName;
        this.taskCreatedAt = taskCreatedAt;
    }
}
