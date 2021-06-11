package com.example.todoc.data.entity;


import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "project_table")
public class ProjectEntity {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    @NonNull
    String projectName;

    @ColorInt
    int colorProject;

    public ProjectEntity(int id, @NonNull String projectName,@ColorInt int colorProject) {
        this.id = id;
        this.projectName = projectName;
        this.colorProject = colorProject;
    }


}
