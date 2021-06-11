package com.example.todoc;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.todoc.data.database.AppDatabase;
import com.example.todoc.repository.TaskRepository;
import com.example.todoc.task.TasksViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;
    private final ExecutorService executorService;
    private final TaskRepository taskRepository;

    public ViewModelFactory(AppDatabase appDatabase, ExecutorService executorService, TaskRepository taskRepository) {
        this.executorService = executorService;
        this.taskRepository = taskRepository;
    }


    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {

                    RoomDatabase.Builder<AppDatabase> builder = Room.databaseBuilder(
                        MainApplication.getApplication(),
                        AppDatabase.class,
                        "app_database"
                    ).addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                            // TODO FADEL Insert initial projects
                            db.beginTransaction();

                            // db.execSQL();
                        }
                    });
                    if (BuildConfig.DEBUG) {
                        builder.fallbackToDestructiveMigration();
                    }

                    AppDatabase database = builder.build();
                    factory = new ViewModelFactory(
                        database,
                        Executors.newFixedThreadPool(4),
                        new TaskRepository(database.getTaskDao(), database.getProjectDao())
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
            return (T) new TasksViewModel(taskRepository, executorService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

