package com.example.todoc.addtask;

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




}
