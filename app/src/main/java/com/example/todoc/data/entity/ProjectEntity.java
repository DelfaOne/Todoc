package com.example.todoc.data.entity;


import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "project_table")
public class ProjectEntity {

    @PrimaryKey(autoGenerate = true)
    public long id = 0;

    @NonNull
    public String projectName;

    @ColorInt
    public int colorProject;

    @Ignore
    public ProjectEntity(@NonNull String projectName,@ColorInt int colorProject) {
        this.projectName = projectName;
        this.colorProject = colorProject;
    }

    public ProjectEntity(long id, @NonNull String projectName,@ColorInt int colorProject) {
        this.id = id;
        this.projectName = projectName;
        this.colorProject = colorProject;
    }


}
