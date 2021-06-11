package com.example.todoc.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ProjectWithTasks {

    @Embedded
    public ProjectEntity project;

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    public List<TasksEntity> tasks;

}
