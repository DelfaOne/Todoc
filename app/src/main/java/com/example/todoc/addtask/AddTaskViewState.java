package com.example.todoc.addtask;

import java.util.Objects;

public class AddTaskViewState {

    private final String Task;

    private final String taskError;

    private final String Project;

    private final String projectError;

    private final boolean isButtonEnable;

    public AddTaskViewState(String task, String taskError, String project, String projectError, boolean isButtonEnable) {
        Task = task;
        this.taskError = taskError;
        Project = project;
        this.projectError = projectError;
        this.isButtonEnable = isButtonEnable;
    }

    public String getTask() {
        return Task;
    }

    public String getTaskError() {
        return taskError;
    }

    public String getProject() {
        return Project;
    }

    public String getProjectError() {
        return taskError;
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
                Objects.equals(Task, that.Task) &&
                Objects.equals(taskError, that.taskError) &&
                Objects.equals(Project, that.Project) &&
                Objects.equals(taskError, that.taskError);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Task, taskError, Project, taskError, isButtonEnable);
    }

    @Override
    public String toString() {
        return "AddTaskViewState{" +
                "Task='" + Task + '\'' +
                ", subjectError='" + taskError + '\'' +
                ", Project='" + Project + '\'' +
                ", projectError='" + taskError + '\'' +
                ", isButtonEnable=" + isButtonEnable +
                '}';
    }
}
