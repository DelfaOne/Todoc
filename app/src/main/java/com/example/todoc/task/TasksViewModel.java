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
import java.util.concurrent.ExecutorService;

public class TasksViewModel extends ViewModel {

    private final TaskRepository taskRepository;
    private final Executor executorService;

    private final MediatorLiveData<List<TaskUiModel>> _taskUiModelLiveData = new MediatorLiveData<>();
    public final LiveData<List<TaskUiModel>> taskUiModelLiveData = _taskUiModelLiveData;

    public TasksViewModel(TaskRepository taskRepository, Executor executorService) {
        this.taskRepository = taskRepository;
        this.executorService = executorService;

        LiveData<List<TasksEntity>> taskLiveData = taskRepository.getAllTasks();
        LiveData<List<ProjectEntity>> projectLiveData = taskRepository.getAllProjects();


        _taskUiModelLiveData.addSource(taskLiveData, new Observer<List<TasksEntity>>() {
            @Override
            public void onChanged(List<TasksEntity> tasksEntities) {
                combine(tasksEntities, projectLiveData.getValue());
            }
        });

        _taskUiModelLiveData.addSource(projectLiveData, new Observer<List<ProjectEntity>>() {
            @Override
            public void onChanged(List<ProjectEntity> projectEntities) {
                combine(taskLiveData.getValue(), projectEntities);
            }
        });
    }

    private void combine(@Nullable List<TasksEntity> tasksEntities,@Nullable List<ProjectEntity> projectEntities) {
        if (tasksEntities == null || projectEntities == null) {
            return;
        }

        List<TaskUiModel> results = new ArrayList<>();

        for (TasksEntity tasksEntity : tasksEntities) {
            for (ProjectEntity projectEntity : projectEntities) {
                if (tasksEntity.projectId == projectEntity.id) {
                    results.add(map(tasksEntity, projectEntity));
                }
            }
        }

        _taskUiModelLiveData.setValue(results);
    }

    public void onCLicked() {
        executorService.execute(() -> {
            taskRepository.createProject(new ProjectEntity(1, "Projet 1", 0xFF3700B3));
            taskRepository.insertAll(populateData());
        });
    }

    public static TasksEntity[] populateData() {
        return new TasksEntity[]{
                new TasksEntity(0, 1, "Tâche 1", "23-06-2021"),
                new TasksEntity(1, 1, "Tâche 2", "23-07-2021"),
                new TasksEntity(2, 1, "Tâche 3", "23-08-2021"),
                new TasksEntity(3, 1, "Tâche 4", "23-09-2021"),
                new TasksEntity(4, 1, "Tâche 5", "23-10-2021"),
        };
    }

    private TaskUiModel map(TasksEntity tasksEntity, ProjectEntity projectEntity) {
        return new TaskUiModel(
                tasksEntity.id,
                tasksEntity.taskName,
                projectEntity.projectName,
                projectEntity.colorProject
                );
    }


}
