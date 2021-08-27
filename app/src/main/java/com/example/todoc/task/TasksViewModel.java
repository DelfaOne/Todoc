package com.example.todoc.task;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.todoc.data.entity.ProjectEntity;
import com.example.todoc.data.entity.TasksEntity;
import com.example.todoc.repository.SelectedProjectsIdRepository;
import com.example.todoc.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;

public class TasksViewModel extends ViewModel {

    private final TaskRepository taskRepository;
    private final SelectedProjectsIdRepository selectedProjectsIdRepository;
    private final Executor executorService;

    private final MediatorLiveData<TaskViewState> _taskViewStateLiveData = new MediatorLiveData<>();
    public final LiveData<TaskViewState> taskViewStateLiveData = _taskViewStateLiveData;
    private final MutableLiveData<Boolean> isSortingStateAscendantLiveData = new MutableLiveData<>();

    public TasksViewModel(TaskRepository taskRepository, SelectedProjectsIdRepository selectedProjectsIdRepository, Executor executorService) {
        this.taskRepository = taskRepository;
        this.selectedProjectsIdRepository = selectedProjectsIdRepository;
        this.executorService = executorService;

        LiveData<List<TasksEntity>> taskLiveData = taskRepository.getAllTasks();
        LiveData<List<ProjectEntity>> projectLiveData = taskRepository.getAllProjects();
        LiveData<List<Long>> selectedProjectLiveData = selectedProjectsIdRepository.getIdProjectListLiveData();

        _taskViewStateLiveData.addSource(taskLiveData, tasksEntities -> combine(tasksEntities, projectLiveData.getValue(), selectedProjectLiveData.getValue(), isSortingStateAscendantLiveData.getValue()));

        _taskViewStateLiveData.addSource(projectLiveData, projectEntities -> combine(taskLiveData.getValue(), projectEntities, selectedProjectLiveData.getValue(), isSortingStateAscendantLiveData.getValue()));

        _taskViewStateLiveData.addSource(selectedProjectLiveData, projectsSelected -> combine(taskLiveData.getValue(), projectLiveData.getValue(), projectsSelected, isSortingStateAscendantLiveData.getValue()));

        _taskViewStateLiveData.addSource(isSortingStateAscendantLiveData, isSortingStateAscendant -> combine(taskLiveData.getValue(), projectLiveData.getValue(), selectedProjectLiveData.getValue(), isSortingStateAscendant));


    }

    private void combine(@Nullable List<TasksEntity> tasksEntities, @Nullable List<ProjectEntity> projectEntities, List<Long> selectedProjects, Boolean isSortingStateAscendant) {
        if (tasksEntities == null || projectEntities == null) {
            return;
        }

        List<TaskViewStateItem> results = new ArrayList<>();
        List<TasksEntity> copiedTaskEntities = new ArrayList<>(tasksEntities);

        if (isSortingStateAscendant != null) {
            if (isSortingStateAscendant) {
                Collections.sort(copiedTaskEntities, (o1, o2) -> o2.getTaskCreatedAt().compareTo(o2.getTaskCreatedAt()));
            } else {
                Collections.sort(copiedTaskEntities, (o1, o2) -> o1.getTaskCreatedAt().compareTo(o2.getTaskCreatedAt()));
            }
        }

        for (TasksEntity tasksEntity : tasksEntities) {
            for (ProjectEntity projectEntity : projectEntities) {
                if (tasksEntity.getProjectId() == projectEntity.getId()) {
                    if (selectedProjects.isEmpty()) {
                        results.add(map(tasksEntity, projectEntity));
                    } else {
                        for (Long selectedProject : selectedProjects) {
                            if (selectedProjects.isEmpty() || tasksEntity.getProjectId() == selectedProject) {
                                results.add(map(tasksEntity, projectEntity));
                            }
                        }
                    }
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
                tasksEntity.getId(),
                tasksEntity.getTaskName(),
                projectEntity.getProjectName(),
                projectEntity.getColorProject()
        );
    }


    public void deleteTask(long tasksId) {
        executorService.execute(() -> taskRepository.deleteTask(tasksId));
    }

    public void onDateSortingButtonSelected() {
        Boolean previousValue = isSortingStateAscendantLiveData.getValue();
        if (previousValue == null) {
            isSortingStateAscendantLiveData.setValue(true);
        } else {
            isSortingStateAscendantLiveData.setValue(!previousValue);
        }
    }

}
