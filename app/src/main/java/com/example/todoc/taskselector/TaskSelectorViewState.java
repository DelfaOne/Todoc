package com.example.todoc.taskselector;

import androidx.annotation.NonNull;

import java.util.Objects;

public class TaskSelectorViewState {

    @NonNull
    private final String projectName;

    private final long id;

    private final boolean isSelected;

    public TaskSelectorViewState(@NonNull String projectName, long id, boolean isSelected) {
        this.projectName = projectName;
        this.id = id;
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "TaskSelectorViewState{" +
                "projectName='" + projectName + '\'' +
                ", id=" + id +
                ", isSelected=" + isSelected +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskSelectorViewState that = (TaskSelectorViewState) o;
        return id == that.id &&
                isSelected == that.isSelected &&
                projectName.equals(that.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectName, id, isSelected);
    }

    @NonNull
    public String getProjectName() {
        return projectName;
    }

    public long getId() {
        return id;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
