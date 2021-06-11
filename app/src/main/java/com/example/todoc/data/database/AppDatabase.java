package com.example.todoc.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todoc.data.dao.ProjectDao;
import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entity.ProjectEntity;
import com.example.todoc.data.entity.TasksEntity;

@Database(entities = {TasksEntity.class, ProjectEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao getTaskDao();
    public abstract ProjectDao getProjectDao();


}
