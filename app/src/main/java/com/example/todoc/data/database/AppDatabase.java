package com.example.todoc.data.database;

import android.app.Application;

import androidx.annotation.NonNull;
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
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                ioExecutor.execute(() -> {
                    ProjectDao projectDao = AppDatabase.getInstance(application, ioExecutor).getProjectDao();

                    projectDao.upsert(
                            new ProjectEntity(
                                    "Projet 2",
                                    ResourcesCompat.getColor(application.getResources(), R.color.color_project, null)
                            )
                    );
                    projectDao.upsert(
                            new ProjectEntity(
                                    "Projet 3",
                                    ResourcesCompat.getColor(application.getResources(), R.color.color_project, null)
                            )
                    );
                    projectDao.upsert(
                            new ProjectEntity(
                                    "Projet 4",
                                    ResourcesCompat.getColor(application.getResources(), R.color.color_project, null)
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
