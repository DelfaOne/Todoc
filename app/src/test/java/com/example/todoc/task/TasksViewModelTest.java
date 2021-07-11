package com.example.todoc.task;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.todoc.data.dao.ProjectDao;
import com.example.todoc.data.dao.TaskDao;
import com.example.todoc.data.entity.ProjectEntity;
import com.example.todoc.data.entity.TasksEntity;
import com.example.todoc.repository.TaskRepository;
import com.example.todoc.utils.TestExecutor;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@RunWith(MockitoJUnitRunner.class)
public class TasksViewModelTest extends TestCase {

    private static final String EXPECTED_PROJECT_NAME = "EXPECTED_PROJECT_NAME";
    private static final int EXPECTED_PROJECT_COLOR = 0;

    @Mock
    TaskRepository taskRepository;

    private final Executor executor = new TestExecutor();
    private TasksViewModel tasksViewModel;

    private final MutableLiveData<List<TasksEntity>> taskLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<ProjectEntity>> projectLiveData = new MutableLiveData<>();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {

        projectLiveData.setValue(getDummyProjects());

        Mockito.doReturn(taskLiveData).when(taskRepository).getAllTasks();
        Mockito.doReturn(projectLiveData).when(taskRepository).getAllProjects();

        tasksViewModel = new TasksViewModel(taskRepository, executor);

        Mockito.verify(taskRepository).getAllTasks();
        Mockito.verify(taskRepository).getAllProjects();
    }

    @Test
    public void whenDeleteTaskClickedShouldCallTaskRepository() {
        //WHEN
        tasksViewModel.deleteTask(1);

        //THEN
        Mockito.verify(taskRepository, Mockito.times(1)).deleteTask(1);
        Mockito.verifyNoMoreInteractions(taskRepository);
    }

    private void fillDummyTask(ArrayList<ProjectEntity> listTask) {
        listTask.add(new ProjectEntity(
                EXPECTED_PROJECT_NAME,
                EXPECTED_PROJECT_COLOR
        ));
    }

    private ArrayList<ProjectEntity> getDummyProjects() {
        ArrayList<ProjectEntity> arrayList = new ArrayList<>();
        fillDummyTask(arrayList);
        return arrayList;
    }


}