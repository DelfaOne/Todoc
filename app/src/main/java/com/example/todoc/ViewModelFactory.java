package com.example.todoc;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.viewbinding.BuildConfig;

import com.example.todoc.addtask.AddTaskViewModel;
import com.example.todoc.data.database.AppDatabase;
import com.example.todoc.data.entity.TasksEntity;
import com.example.todoc.repository.TaskRepository;
import com.example.todoc.task.TasksViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;
    private final Executor executorService;
    private final TaskRepository taskRepository;

    public ViewModelFactory(Executor executorService, TaskRepository taskRepository) {
        this.executorService = executorService;
        this.taskRepository = taskRepository;
    }


    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    Executor executor = Executors.newFixedThreadPool(4);
                    AppDatabase appDatabase = AppDatabase.getInstance(MainApplication.getApplication(), executor);
                    factory = new ViewModelFactory(executor, new TaskRepository(appDatabase.getTaskDao(), appDatabase.getProjectDao()));
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
        if (modelClass.isAssignableFrom(AddTaskViewModel.class)) {
            return (T) new AddTaskViewModel(taskRepository, MainApplication.getApplication());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

