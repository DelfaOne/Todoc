package com.example.todoc.taskselector;

import static com.example.todoc.utils.UnitTestUtils.getOrAwaitValue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.todoc.data.entity.ProjectEntity;
import com.example.todoc.repository.SelectedProjectsIdRepository;
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
public class TaskSelectorViewModelTest extends TestCase {

    @Mock
    TaskRepository taskRepository;

    @Mock
    SelectedProjectsIdRepository selectedProjectsIdRepository;

    private final Executor executor = new TestExecutor();

    private TaskSelectorViewModel taskSelectorViewModel;

    private final MutableLiveData<List<ProjectEntity>> projectLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Long>> selectedProjectsLiveData = new MutableLiveData<>();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        List<ProjectEntity> projectList = new ArrayList<>();
        projectList.add(new ProjectEntity(1, "Projet Tartampion", 0));
        projectList.add(new ProjectEntity(2, "Projet Lucidia", 0));
        projectList.add(new ProjectEntity(3, "Projet Circus", 0));
        projectLiveData.setValue(projectList);

        List<Long> selectedProjectIdList = new ArrayList<>();
        selectedProjectIdList.add(2L);
        selectedProjectsLiveData.setValue(selectedProjectIdList);

        Mockito.doReturn(projectLiveData).when(taskRepository).getAllProjects();
        Mockito.doReturn(selectedProjectsLiveData).when(selectedProjectsIdRepository).getIdProjectListLiveData();

        taskSelectorViewModel = new TaskSelectorViewModel(taskRepository, selectedProjectsIdRepository, executor);

        Mockito.verify(taskRepository).getAllProjects();
    }

    @Test
    public void nominalCaseOnCheckedChange() throws InterruptedException {

        //WHEN
        List<TaskSelectorViewState> result = getOrAwaitValue(taskSelectorViewModel.getTaskSelectorLiveData());

        List<TaskSelectorViewState> excepted = new ArrayList<>();

        excepted.add(new TaskSelectorViewState("Projet Tartampion", 1, false));
        excepted.add(new TaskSelectorViewState("Projet Lucidia", 2, true));
        excepted.add(new TaskSelectorViewState("Projet Circus", 3, false));

        assertEquals(excepted, result);
    }

    @Test
    public void shouldReturnTheExceptedResultWhenNothingIsSelected() throws InterruptedException {
        //GIVEN
        List<Long> selectedProjectIdList = new ArrayList<>();
        selectedProjectIdList.clear();

        //WHEN
        selectedProjectsLiveData.setValue(selectedProjectIdList);

        List<TaskSelectorViewState> result = getOrAwaitValue(taskSelectorViewModel.getTaskSelectorLiveData());
        List<TaskSelectorViewState> excepted = new ArrayList<>();

        excepted.add(new TaskSelectorViewState("Projet Tartampion", 1, false));
        excepted.add(new TaskSelectorViewState("Projet Lucidia", 2, false));
        excepted.add(new TaskSelectorViewState("Projet Circus", 3, false));

        assertEquals(excepted, result);
    }

    @Test
    public void shouldReturnTheExceptedResultWhenEverythingIsSelected() throws InterruptedException {
        //GIVEN
        List<Long> selectedProjectIdList = new ArrayList<>();
        selectedProjectIdList.add(1L);
        selectedProjectIdList.add(2L);
        selectedProjectIdList.add(3L);

        //WHEN
        selectedProjectsLiveData.setValue(selectedProjectIdList);

        List<TaskSelectorViewState> result = getOrAwaitValue(taskSelectorViewModel.getTaskSelectorLiveData());
        List<TaskSelectorViewState> excepted = new ArrayList<>();

        excepted.add(new TaskSelectorViewState("Projet Tartampion", 1, true));
        excepted.add(new TaskSelectorViewState("Projet Lucidia", 2, true));
        excepted.add(new TaskSelectorViewState("Projet Circus", 3, true));

        assertEquals(excepted, result);
    }

}