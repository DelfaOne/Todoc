package com.example.todoc.tasks;

import androidx.lifecycle.ViewModel;

import com.example.todoc.data.ProjectDao;
import com.example.todoc.data.entity.TasksEntity;

import java.util.concurrent.ExecutorService;

public class TasksViewModel extends ViewModel {

    private final ProjectDao projectDao;
    private final ExecutorService executorService;

    public TasksViewModel(ProjectDao projectDao, ExecutorService executorService) {
        this.projectDao = projectDao;
        this.executorService = executorService;
    }

    public void onCLicked() {
        executorService.execute(() -> {
            projectDao.upsert(new TasksEntity("TOTO"));
        });
    }


}
