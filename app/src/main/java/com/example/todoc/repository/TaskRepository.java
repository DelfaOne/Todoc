package com.example.todoc.repository;

import androidx.lifecycle.LiveData;

import com.example.todoc.data.dao.ProjectDao;
import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entity.TasksEntity;

import java.util.List;

public class TaskRepository {

    private final TaskDao taskDao;
    private final ProjectDao projectDao;
    private LiveData<List<TasksEntity>> tasksEntityList;


    public TaskRepository(TaskDao taskDao, ProjectDao projectDao) {
        this.taskDao = taskDao;
        this.projectDao = projectDao;
        tasksEntityList = taskDao.getAll();
    }

    public LiveData<List<TasksEntity>> getAllTasks() {
        return tasksEntityList;
    }

    public void deleteTask(TasksEntity tasksEntity) {
        taskDao.delete(tasksEntity);
    }

    public void createTask(TasksEntity tasksEntity) {
        taskDao.upsert(tasksEntity);
    }


}
