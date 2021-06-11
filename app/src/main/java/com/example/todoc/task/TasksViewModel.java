package com.example.todoc.task;

import androidx.lifecycle.ViewModel;

import com.example.todoc.data.entity.TasksEntity;
import com.example.todoc.repository.TaskRepository;

import java.util.concurrent.ExecutorService;

public class TasksViewModel extends ViewModel {

    private final TaskRepository taskRepository;
    private final ExecutorService executorService;

    public TasksViewModel(TaskRepository taskRepository, ExecutorService executorService) {
        this.taskRepository = taskRepository;
        this.executorService = executorService;
    }

    public void onCLicked() {
        executorService.execute(() -> {
            taskRepository.createTask(new TasksEntity(
                    1,
                    3,
                    "test",
                    "test"
            ));
        });
    }


}
