package com.example.todoc.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.todoc.R;
import com.example.todoc.ViewModelFactory;
import com.example.todoc.databinding.TodocListFragmentBinding;

import java.util.List;

public class TasksFragment extends Fragment {

    private TodocListFragmentBinding vb;
    private TasksViewModel vm;
    private TaskAdapter taskAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vb = TodocListFragmentBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(TasksViewModel.class);
        init();
        View view = vb.getRoot();
        setHasOptionsMenu(true);
        setupAdapter();
        return view;
    }

    private void init() {

        vb.fabAddTask.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_todocListFragment_to_addTaskFragment);
            vm.onCLicked();
        });
    }

    private void setupAdapter() {
        vb.taskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskAdapter = new TaskAdapter();
        vb.taskRecyclerView.setAdapter(taskAdapter);
        vm.taskUiModelLiveData.observe(getViewLifecycleOwner(), new Observer<List<TaskUiModel>>() {
            @Override
            public void onChanged(List<TaskUiModel> taskUiModels) {
                taskAdapter.submitList(taskUiModels);
            }
        });
    }
}
