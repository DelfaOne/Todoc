package com.example.todoc.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entity.TasksEntity;

@Database(entities = {TasksEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao getProjectDao();


}
