package com.example.todoc.taskselector;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.todoc.data.entity.ProjectEntity;
import com.example.todoc.repository.SelectedProjectsIdRepository;
import com.example.todoc.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class TaskSelectorViewModel extends ViewModel {

    @NonNull
    private final TaskRepository taskRepository;

    private final SelectedProjectsIdRepository selectedProjectsIdRepository;

    private final Executor executorService;

    private final MediatorLiveData<List<TaskSelectorViewState>> taskSelectorLiveData;

    public TaskSelectorViewModel(@NonNull TaskRepository taskRepository, SelectedProjectsIdRepository selectedProjectsIdRepository, Executor executorService) {
        this.taskRepository = taskRepository;
        this.executorService = executorService;
        this.selectedProjectsIdRepository = selectedProjectsIdRepository;

        LiveData<List<ProjectEntity>> projectEntitiesLiveData = taskRepository.getAllProjects();
        LiveData<List<Long>> selectedProjectLiveData = selectedProjectsIdRepository.getIdProjectListLiveData();

        taskSelectorLiveData = new MediatorLiveData<>();
        taskSelectorLiveData.addSource(projectEntitiesLiveData, projectEntities -> combine(projectEntities, selectedProjectLiveData.getValue()));
        taskSelectorLiveData.addSource(selectedProjectLiveData, projectIds -> combine(projectEntitiesLiveData.getValue(), projectIds));


    }

    private void combine(List<ProjectEntity> projectEntities, List<Long> projectIds) {
        if (projectEntities == null || projectIds == null) {
            return;
        }

        List<TaskSelectorViewState> taskSelectorViewStates = new ArrayList<>();

        for (ProjectEntity projectEntity : projectEntities) {
            boolean found = false;
            for (Long projectId : projectIds) {
                if (projectEntity.getId() == projectId) {
                    found = true;
                    break;
                }
            }
            taskSelectorViewStates.add(new TaskSelectorViewState(
                    projectEntity.getProjectName(),
                    projectEntity.getId(),
                    found
            ));

        }
        taskSelectorLiveData.setValue(taskSelectorViewStates);
    }

    public void onCheckedChange(boolean isChecked, long id) {
        if (isChecked) {
            selectedProjectsIdRepository.deleteId(id);
        } else {
            selectedProjectsIdRepository.addId(id);
        }
    }

    public LiveData<List<TaskSelectorViewState>> getTaskSelectorLiveData() {
        return taskSelectorLiveData;
    }
}
