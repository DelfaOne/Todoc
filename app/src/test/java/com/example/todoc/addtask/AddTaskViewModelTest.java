package com.example.todoc.addtask;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.todoc.R;
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

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Executor;

@RunWith(MockitoJUnitRunner.class)
public class AddTaskViewModelTest extends TestCase {

    AddTaskViewModel addTaskViewModel;

    @Mock
    TaskRepository taskRepository;

    @Mock
    Application application;

    private final Executor executor = new TestExecutor();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    Clock clock = Clock.fixed(Instant.ofEpochMilli(1616782896000L), ZoneOffset.UTC);

    @Before
    public void setup() {
        addTaskViewModel = new AddTaskViewModel(taskRepository, application, executor, clock);
    }

    @Test
    public void nominalCaseAddNewTask() {
        //GIVEN
        String taskName = "taskName";
        long projectId = 2L;

        //WHEN
        addTaskViewModel.onTaskNameChange(taskName);
        addTaskViewModel.onProjectChange(projectId);
        addTaskViewModel.onButtonAddClick();

        //THEN
        Mockito.verify(taskRepository).createTask(
                new TasksEntity(
                        0,
                        projectId,
                        taskName,
                        LocalDateTime.now(clock)
                )
        );
        Mockito.verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void verifyOnTaskNameChange() {
        //GIVEN
        String taskName = "Toto";

        //WHEN
        addTaskViewModel.onTaskNameChange(taskName);

        //THEN
        addTaskViewModel.viewStateLiveData.observeForever(addTaskViewStateResult -> {
            final AddTaskViewState excepted = new AddTaskViewState(
                    taskName,
                    null,
                    null,
                    false
            );
            assertEquals(excepted, addTaskViewStateResult);
        });
    }

    @Test
    public void shouldDisplayTaskErrorWhenTaskIsEmpty() {
        //GIVEN
        String emptyTask = null;
        Mockito.doReturn("taskError").when(application).getString(R.string.taskError);

        //WHEN
        addTaskViewModel.onTaskNameChange(emptyTask);

        //THEN
        addTaskViewModel.viewStateLiveData.observeForever(addTaskViewStateResult -> {
            final AddTaskViewState excepted = new AddTaskViewState(
                    emptyTask,
                    "taskError",
                    null,
                    false
            );
            //Check state
            assertEquals(excepted, addTaskViewStateResult);

        });
    }

    @Test
    public void shouldDisplayProjectErrorWhenProjectIsEmpty() {
        //GIVEN
        String emptyProject = null;
        Mockito.doReturn("projectError").when(application).getString(R.string.projectError);

        //WHEN
        addTaskViewModel.onTaskNameChange(emptyProject);

        //THEN
        addTaskViewModel.viewStateLiveData.observeForever(addTaskViewStateResult -> {
            final AddTaskViewState excepted = new AddTaskViewState(
                    null,
                    null,
                    "projectError",
                    false
            );
            //Check state
            assertEquals(excepted, addTaskViewStateResult);

        });
    }

    @Test
    public void whenTaskNameAndProjectAreFilledButtonShouldBeEnable() {
        //GIVEN
        String taskName = "taskName";
        long projectId = 2L;

        //WHEN
        addTaskViewModel.onTaskNameChange(taskName);
        addTaskViewModel.onProjectChange(projectId);

        //THEN
        addTaskViewModel.viewStateLiveData.observeForever(addTaskViewStateResult -> {
            final AddTaskViewState excepted = new AddTaskViewState(
                    taskName,
                    null,
                    null,
                    true
            );
            //Check state
            assertEquals(excepted, addTaskViewStateResult);

        });
    }
}