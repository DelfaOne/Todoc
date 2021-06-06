package com.example.todoc.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Represent table in SQLite database

@Entity(tableName = "Tasks")
public class TasksEntity {

    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    public String taskName;


    public TasksEntity(String taskName) {
        this.taskName = taskName;
    }

}
