package com.example.todoc.data.database;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.viewbinding.BuildConfig;

import com.example.todoc.MainApplication;
import com.example.todoc.R;
import com.example.todoc.data.converters.Converters;
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
        version = 2,
        exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "app_database";

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(
            @NonNull Application application,
            @NonNull Executor ioExecutor,
            @NonNull DatabaseInitializer databaseInitializer
    ) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = create(application, ioExecutor, databaseInitializer);
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase create(
        @NonNull Application application,
        @NonNull Executor ioExecutor,
        @NonNull DatabaseInitializer databaseInitializer) {
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
                    ProjectDao projectDao = AppDatabase.getInstance(application, ioExecutor, databaseInitializer).getProjectDao();
                    TaskDao taskDao = AppDatabase.getInstance(application, ioExecutor, databaseInitializer).getTaskDao();

                    databaseInitializer.initialize(application, projectDao, taskDao);
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
