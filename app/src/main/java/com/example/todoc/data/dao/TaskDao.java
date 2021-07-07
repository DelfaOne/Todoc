package com.example.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.todoc.data.entity.TasksEntity;

import java.util.List;

//Contient les requêtes SQL
@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(TasksEntity tasksEntity);

    @Query("SELECT * FROM tasks_table")
    LiveData<List<TasksEntity>> getAll();

    @Query("DELETE FROM tasks_table")
    void deleteAllTasks();

    @Query("DELETE FROM tasks_table WHERE id=:taskId")
    void deleteTaskById(long taskId);

    /*@Query("SELECT * FROM tasks_table WHERE taskName = :taskName")
    LiveData<List<TasksEntity>>getTasksByName(String taskName);*/


}
