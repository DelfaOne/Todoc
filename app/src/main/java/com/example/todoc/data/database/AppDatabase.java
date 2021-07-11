package com.example.todoc.data.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.viewbinding.BuildConfig;

import com.example.todoc.MainApplication;
import com.example.todoc.R;
import com.example.todoc.data.dao.ProjectDao;
import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entity.ProjectEntity;
import com.example.todoc.data.entity.TasksEntity;

import java.time.LocalDateTime;
import java.util.concurrent.Executor;

@Database(
        entities = {
                TasksEntity.class,
                ProjectEntity.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "app_database";

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(
            @NonNull Application application,
            @NonNull Executor ioExecutor
    ) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = create(application, ioExecutor);
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase create(
            @NonNull Application application,
            @NonNull Executor ioExecutor
    ) {
        Builder<AppDatabase> builder = Room.databaseBuilder(
                MainApplication.getApplication(),
                AppDatabase.class,
                DATABASE_NAME
        );

        builder.addCallback(new Callback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                ioExecutor.execute(() -> {
                    ProjectDao projectDao = AppDatabase.getInstance(application, ioExecutor).getProjectDao();
                    TaskDao taskDao = AppDatabase.getInstance(application, ioExecutor).getTaskDao();

                    //Populate project
                    projectDao.upsert(
                            new ProjectEntity(
                                    "Projet Tartampion",
                                    ResourcesCompat.getColor(application.getResources(), R.color.first_project, null)
                            )
                    );
                    projectDao.upsert(
                            new ProjectEntity(
                                    "“Projet Lucidia",
                                    ResourcesCompat.getColor(application.getResources(), R.color.second_project, null)
                            )
                    );
                    projectDao.upsert(
                            new ProjectEntity(
                                    "Projet Circus",
                                    ResourcesCompat.getColor(application.getResources(), R.color.third_project, null)
                            )
                    );

                    //Populate Task
                    taskDao.upsert(
                            new TasksEntity(
                                    1,
                                    1,
                                    "Task 1",
                                    LocalDateTime.now().plusDays(1).toString()
                                    )
                    );
                    taskDao.upsert(
                            new TasksEntity(
                                    2,
                                    2,
                                    "Task 2",
                                    LocalDateTime.now().plusDays(2).toString()
                            )
                    );
                    taskDao.upsert(
                            new TasksEntity(
                                    3,
                                    3,
                                    "Task 3",
                                    LocalDateTime.now().plusDays(3).toString()
                            )
                    );
                    taskDao.upsert(
                            new TasksEntity(
                                    2,
                                    4,
                                    "Task 4",
                                    LocalDateTime.now().plusDays(4).toString()
                            )
                    );
                    taskDao.upsert(
                            new TasksEntity(
                                    3,
                                    5,
                                    "Task 5",
                                    LocalDateTime.now().plusDays(5).toString()
                            )
                    );

                });
            }
        });

        if (BuildConfig.DEBUG) {
            builder.fallbackToDestructiveMigration();
        }

        return builder.build();
    }

    public abstract TaskDao getTaskDao();

    public abstract ProjectDao getProjectDao();

}
