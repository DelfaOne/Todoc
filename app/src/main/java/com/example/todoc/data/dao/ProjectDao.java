package com.example.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.todoc.data.entity.ProjectEntity;

public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(ProjectEntity projectEntity);

    @Query("DELETE FROM project_table")
    void deleteAll();

    @Query("Select * FROM project_table")
    LiveData<ProjectEntity> getAllProjects();
}
