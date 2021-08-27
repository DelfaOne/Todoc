package com.example.todoc.addtask;

import java.util.Objects;

public class AddTaskViewState {

    private final String taskDescription;

    private final String taskError;

    private final String projectError;

    private final boolean isButtonEnable;

    public AddTaskViewState(String taskDescription, String taskError, String projectError, boolean isButtonEnable) {
        this.taskDescription = taskDescription;
        this.taskError = taskError;
        this.projectError = projectError;
        this.isButtonEnable = isButtonEnable;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskError() {
        return taskError;
    }

    public String getProjectError() {
        return projectError;
    }

    public boolean isButtonEnable() {
        return isButtonEnable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddTaskViewState that = (AddTaskViewState) o;
        return isButtonEnable == that.isButtonEnable &&
                Objects.equals(taskDescription, that.taskDescription) &&
                Objects.equals(taskError, that.taskError) &&
                Objects.equals(projectError, that.projectError);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskDescription, taskError, projectError, isButtonEnable);
    }

    @Override
    public String toString() {
        return "AddTaskViewState{" +
                "taskDescription='" + taskDescription + '\'' +
                ", taskError='" + taskError + '\'' +
                ", projectError='" + projectError + '\'' +
                ", isButtonEnable=" + isButtonEnable +
                '}';
    }
}
