package com.example.todoc.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.todoc.data.entity.TasksEntity;

import java.util.List;

//Contient les requêtes SQL
@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(TasksEntity tasksEntity);
    @Query("SELECT * FROM Tasks")
    LiveData<List<TasksEntity>> getAll();


}
