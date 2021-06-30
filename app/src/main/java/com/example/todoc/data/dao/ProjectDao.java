package com.example.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.todoc.data.entity.ProjectEntity;
import com.example.todoc.data.entity.ProjectWithTasks;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(ProjectEntity projectEntity);

    @Query("DELETE FROM project_table")
    void deleteAll();

    @Query("Select * FROM project_table")
    LiveData<List<ProjectEntity>> getAllProjects();

    @Transaction
    @Query("SELECT * FROM project_table WHERE id == :projectId")
    LiveData<ProjectWithTasks> getProjectWithTasksForId(int projectId);
}
