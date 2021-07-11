package com.example.todoc.addtask;

import android.app.Application;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoc.R;
import com.example.todoc.data.entity.TasksEntity;
import com.example.todoc.repository.TaskRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class AddTaskViewModel extends ViewModel {

    private final TaskRepository taskRepository;
    private final Application application;

    private final MutableLiveData<AddTaskViewState> _viewStateLiveData = new MutableLiveData<>();
    public final LiveData<AddTaskViewState> viewStateLiveData = _viewStateLiveData;

    @Nullable
    private String taskName;

    @Nullable
    private String projectName;

    public AddTaskViewModel(TaskRepository taskRepository, Application application) {
        this.taskRepository = taskRepository;
        this.application = application;
    }

    public void onTaskNameChange(String taskName) {
        this.taskName = taskName;
        controlInput();
    }

    public void onProjectChange(String projectName) {
        this.projectName = projectName;
        controlInput();
        //TODO ID
    }

    public void onButtonAddClick() {
        if (controlAdd()) {
            //TODO Executor
            taskRepository.createTask(
                    new TasksEntity(
                            0,
                            taskName,
                            LocalDateTime.now(Clock.systemDefaultZone()).toString()
                            )
            );
        }
    }

    private void controlInput() {
        String taskError = null;
        String projectError = null;

        if (taskName == null || taskName.isEmpty()) {
            taskError = application.getString(R.string.taskError);
        }

        if (projectName == null || projectName.isEmpty()) {
            projectError = application.getString(R.string.projectError);
        }

        _viewStateLiveData.setValue(new AddTaskViewState(
                taskName,
                taskError,
                projectName,
                projectError,
                controlAdd()

        ));


    }

    private boolean controlAdd() {
        return taskName != null && projectName != null;
    }
}
