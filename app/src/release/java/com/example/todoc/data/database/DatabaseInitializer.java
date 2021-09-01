package com.example.todoc.data.database;

import android.app.Application;

import androidx.core.content.res.ResourcesCompat;

import com.example.todoc.R;
import com.example.todoc.data.dao.ProjectDao;
import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entity.ProjectEntity;
import com.example.todoc.data.entity.TasksEntity;

import java.time.LocalDateTime;

public class DatabaseInitializer {

    public void initialize(Application application, ProjectDao projectDao, TaskDao taskDao) {
        //Populate project
        projectDao.upsert(
            new ProjectEntity(
                "Projet Tartampion",
                ResourcesCompat.getColor(application.getResources(), R.color.first_project, null)
            )
        );
        projectDao.upsert(
            new ProjectEntity(
                "Projet Lucidia",
                ResourcesCompat.getColor(application.getResources(), R.color.second_project, null)
            )
        );
        projectDao.upsert(
            new ProjectEntity(
                "Projet Circus",
                ResourcesCompat.getColor(application.getResources(), R.color.third_project, null)
            )
        );
    }

}
