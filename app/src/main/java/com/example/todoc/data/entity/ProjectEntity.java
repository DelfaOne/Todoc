package com.example.todoc.data.entity;


import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "project_table")
public class ProjectEntity {

    @PrimaryKey(autoGenerate = true)
    private final long id;

    @NonNull
    private final String projectName;

    @ColorInt
    private final int colorProject;

    @Ignore
    public ProjectEntity(@NonNull String projectName,@ColorInt int colorProject) {
        this.id = 0;
        this.projectName = projectName;
        this.colorProject = colorProject;
    }

    public ProjectEntity(long id, @NonNull String projectName,@ColorInt int colorProject) {
        this.id = id;
        this.projectName = projectName;
        this.colorProject = colorProject;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getProjectName() {
        return projectName;
    }

    public int getColorProject() {
        return colorProject;
    }
}
