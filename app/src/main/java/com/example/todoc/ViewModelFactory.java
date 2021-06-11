package com.example.todoc;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import com.example.todoc.data.database.AppDatabase;
import com.example.todoc.repository.TaskRepository;
import com.example.todoc.task.TasksViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;
    private final AppDatabase appDatabase;
    private final ExecutorService executorService;
    private final TaskRepository taskRepository;

    public ViewModelFactory(AppDatabase appDatabase, ExecutorService executorService, TaskRepository taskRepository) {
        this.appDatabase = appDatabase;

        this.executorService = executorService;
        this.taskRepository = taskRepository;
    }


    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(
                            Room.databaseBuilder(MainApplication.getApplication(), AppDatabase.class, "app_database").fallbackToDestructiveMigration().build(),
                            Executors.newFixedThreadPool(4),
                            new TaskRepository());
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
            return (T) new TasksViewModel(taskRepository, executorService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

