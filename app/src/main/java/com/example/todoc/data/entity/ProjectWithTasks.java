package com.example.todoc.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ProjectWithTasks {

    @Embedded
    private final ProjectEntity project;

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    private final List<TasksEntity> tasks;

    public ProjectWithTasks(ProjectEntity project, List<TasksEntity> tasks) {
        this.project = project;
        this.tasks = tasks;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public List<TasksEntity> getTasks() {
        return tasks;
    }
}
