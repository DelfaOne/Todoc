package com.example.todoc.task;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

import java.util.Objects;

public class TaskViewStateItem {

    private final long id;

    @NonNull
    private final String taskName;

    @NonNull
    private final String projectName;

    @ColorRes
    private final int colorProject;


    public TaskViewStateItem(long id, @NonNull String taskName, @NonNull String projectName, int colorProject) {
        this.id = id;
        this.taskName = taskName;
        this.projectName = projectName;
        this.colorProject = colorProject;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getTaskName() {
        return taskName;
    }

    @NonNull
    public String getProjectName() {
        return projectName;
    }

    public int getColorProject() {
        return colorProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskViewStateItem that = (TaskViewStateItem) o;
        return id == that.id &&
                colorProject == that.colorProject &&
                taskName.equals(that.taskName) &&
                projectName.equals(that.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskName, projectName, colorProject);
    }

    @Override
    public String toString() {
        return "TodocUiModel{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", colorProject=" + colorProject +
                '}';
    }
}
