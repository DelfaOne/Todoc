package com.example.todoc;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import com.example.todoc.data.AppDatabase;
import com.example.todoc.tasks.TasksViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;
    private final AppDatabase appDatabase;
    private final ExecutorService executorService;

    public ViewModelFactory(AppDatabase appDatabase, ExecutorService executorService) {
        this.appDatabase = appDatabase;

        this.executorService = executorService;
    }


    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(
                            Room.databaseBuilder(MainApplication.getApplication(), AppDatabase.class, "app_database").fallbackToDestructiveMigration().build(),
                            Executors.newFixedThreadPool(4)
                    );
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
            return (T) new TasksViewModel(appDatabase.getProjectDao(), executorService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

