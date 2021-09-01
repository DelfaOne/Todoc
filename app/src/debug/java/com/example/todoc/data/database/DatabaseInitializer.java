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
        Long projectId1 = projectDao.upsert(
            new ProjectEntity(
                "Projet Tartampion",
                ResourcesCompat.getColor(application.getResources(), R.color.first_project, null)
            )
        );
        Long projectId2 = projectDao.upsert(
            new ProjectEntity(
                "Projet Lucidia",
                ResourcesCompat.getColor(application.getResources(), R.color.second_project, null)
            )
        );
        Long projectId3 = projectDao.upsert(
            new ProjectEntity(
                "Projet Circus",
                ResourcesCompat.getColor(application.getResources(), R.color.third_project, null)
            )
        );

        //Populate Task
        taskDao.upsert(
            new TasksEntity(
                projectId1,
                "Task 1",
                LocalDateTime.now().plusDays(100)
            )
        );
        taskDao.upsert(
            new TasksEntity(
                projectId2,
                "Task 2",
                LocalDateTime.now().plusDays(27)
            )
        );
        taskDao.upsert(
            new TasksEntity(
                projectId3,
                "Task 3",
                LocalDateTime.now().plusDays(8)
            )
        );
        taskDao.upsert(
            new TasksEntity(
                projectId3,
                "Task 4",
                LocalDateTime.now().plusDays(68)
            )
        );
        taskDao.upsert(
            new TasksEntity(
                projectId2,
                "Task 5",
                LocalDateTime.now().plusDays(45)
            )
        );
    }

}
