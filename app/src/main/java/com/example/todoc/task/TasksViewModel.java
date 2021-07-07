package com.example.todoc.task;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.todoc.data.entity.ProjectEntity;
import com.example.todoc.data.entity.TasksEntity;
import com.example.todoc.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class TasksViewModel extends ViewModel {

    private final TaskRepository taskRepository;
    private final Executor executorService;

    private final MediatorLiveData<TaskViewState> _taskViewStateLiveData = new MediatorLiveData<>();
    public final LiveData<TaskViewState> taskViewStateLiveData = _taskViewStateLiveData;

    public TasksViewModel(TaskRepository taskRepository, Executor executorService) {
        this.taskRepository = taskRepository;
        this.executorService = executorService;

        LiveData<List<TasksEntity>> taskLiveData = taskRepository.getAllTasks();
        LiveData<List<ProjectEntity>> projectLiveData = taskRepository.getAllProjects();

        _taskViewStateLiveData.addSource(taskLiveData, tasksEntities -> combine(tasksEntities, projectLiveData.getValue()));

        _taskViewStateLiveData.addSource(projectLiveData, projectEntities -> combine(taskLiveData.getValue(), projectEntities));
    }

    private void combine(@Nullable List<TasksEntity> tasksEntities,@Nullable List<ProjectEntity> projectEntities) {
        if (tasksEntities == null || projectEntities == null) {
            return;
        }

        List<TaskViewStateItem> results = new ArrayList<>();

        for (TasksEntity tasksEntity : tasksEntities) {
            for (ProjectEntity projectEntity : projectEntities) {
                if (tasksEntity.projectId == projectEntity.id) {
                    results.add(map(tasksEntity, projectEntity));
                }
            }
        }

        _taskViewStateLiveData.setValue(new TaskViewState(
                results.isEmpty(),
                results
        ));
    }

    private TaskViewStateItem map(TasksEntity tasksEntity, ProjectEntity projectEntity) {
        return new TaskViewStateItem(
                tasksEntity.id,
                tasksEntity.taskName,
                projectEntity.projectName,
                projectEntity.colorProject
                );
    }


    public void deleteTask(long tasksId) {
        executorService.execute(() -> taskRepository.deleteTask(tasksId));
    }

}
