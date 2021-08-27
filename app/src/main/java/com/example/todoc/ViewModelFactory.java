package com.example.todoc;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoc.addtask.AddTaskViewModel;
import com.example.todoc.data.database.AppDatabase;
import com.example.todoc.repository.SelectedProjectsIdRepository;
import com.example.todoc.repository.TaskRepository;
import com.example.todoc.task.TasksViewModel;
import com.example.todoc.taskselector.TaskSelectorViewModel;

import java.time.Clock;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;
    private final Executor executorService;
    private final TaskRepository taskRepository;
    private final SelectedProjectsIdRepository selectedProjectsIdRepository;
    private final Clock clock;

    public ViewModelFactory(Executor executorService, TaskRepository taskRepository, SelectedProjectsIdRepository selectedProjectsIdRepository, Clock clock) {
        this.executorService = executorService;
        this.taskRepository = taskRepository;
        this.selectedProjectsIdRepository = selectedProjectsIdRepository;
        this.clock = clock;
    }


    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    Executor executor = Executors.newFixedThreadPool(4);
                    AppDatabase appDatabase = AppDatabase.getInstance(MainApplication.getApplication(), executor);
                    factory = new ViewModelFactory(executor, new TaskRepository(appDatabase.getTaskDao(), appDatabase.getProjectDao()), new SelectedProjectsIdRepository(), Clock.systemDefaultZone());
                }
            }
        }
        return factory;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TasksViewModel.class)) {
            return (T) new TasksViewModel(taskRepository, selectedProjectsIdRepository, executorService);
        }
        if (modelClass.isAssignableFrom(AddTaskViewModel.class)) {
            return (T) new AddTaskViewModel(taskRepository, MainApplication.getApplication(), executorService, clock);
        }
        if (modelClass.isAssignableFrom(TaskSelectorViewModel.class)) {
            return (T) new TaskSelectorViewModel(taskRepository, selectedProjectsIdRepository, executorService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

