package com.example.todoc.repository;

import androidx.lifecycle.LiveData;

import com.example.todoc.data.dao.ProjectDao;
import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entity.ProjectEntity;
import com.example.todoc.data.entity.TasksEntity;

import java.util.List;

public class TaskRepository {

    private final TaskDao taskDao;
    private final ProjectDao projectDao;
    private LiveData<List<TasksEntity>> tasksEntityList;
    private LiveData<List<ProjectEntity>> projectEntityList;


    public TaskRepository(TaskDao taskDao, ProjectDao projectDao) {
        this.taskDao = taskDao;
        this.projectDao = projectDao;
        tasksEntityList = taskDao.getAll();
        projectEntityList = projectDao.getAllProjects();
    }

    public LiveData<List<TasksEntity>> getAllTasks() {
        return tasksEntityList;
    }

    public LiveData<List<ProjectEntity>> getAllProjects() {
        return projectEntityList;
    }

    public void deleteTask(long taskId) {
        taskDao.deleteTaskById(taskId);
    }

    public void createTask(TasksEntity tasksEntity) {
        taskDao.upsert(tasksEntity);
    }

    public void createProject(ProjectEntity projectEntity) {
        projectDao.upsert(projectEntity);
    }

    public void insertAll(TasksEntity[] tasksEntity) {
        for (TasksEntity entity : tasksEntity) {
            taskDao.upsert(entity);
        }
    }


}
