package com.example.todoc.addtask;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoc.R;
import com.example.todoc.data.entity.ProjectEntity;
import com.example.todoc.data.entity.TasksEntity;
import com.example.todoc.repository.TaskRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executor;

public class AddTaskViewModel extends ViewModel {

    private final TaskRepository taskRepository;
    private final Application application;
    private final Executor executorService;
    private final Clock clock;

    private final MutableLiveData<AddTaskViewState> _viewStateLiveData = new MutableLiveData<>();
    public final LiveData<AddTaskViewState> viewStateLiveData = _viewStateLiveData;

    @Nullable
    private String taskName;

    @Nullable
    private Long projectId;

    public AddTaskViewModel(TaskRepository taskRepository, Application application, Executor executorService, Clock clock) {
        this.taskRepository = taskRepository;
        this.application = application;
        this.executorService = executorService;
        this.clock = clock;
    }

    public LiveData<List<ProjectEntity>> getProjectEntitiesLiveData() {
        return taskRepository.getAllProjects();
    }

    public void onTaskNameChange(String taskName) {
        this.taskName = taskName;
        controlInput();
    }

    public void onProjectChange(Long projectId) {
        this.projectId = projectId;
        controlInput();
    }

    public void onButtonAddClick() {
        if (controlAdd()) {
            executorService.execute(() -> taskRepository.createTask(
                    new TasksEntity(
                            projectId,
                            taskName,
                            LocalDateTime.now(clock)
                    )
            ));
        }
    }

    private void controlInput() {
        String taskError = null;
        String projectError = null;

        if (taskName == null || taskName.isEmpty()) {
            taskError = application.getString(R.string.taskError);
        }

        if (projectId == null) {
            projectError = application.getString(R.string.projectError);
        }

        _viewStateLiveData.setValue(new AddTaskViewState(
                taskName,
                taskError,
                projectError,
                controlAdd()
        ));


    }

    private boolean controlAdd() {
        return taskName != null && projectId != null;
    }
}
