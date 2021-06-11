package com.example.todoc.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.todoc.R;
import com.example.todoc.ViewModelFactory;
import com.example.todoc.databinding.TodocListFragmentBinding;

public class TasksFragment extends Fragment {

    private TodocListFragmentBinding vb;
    private TasksViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vb = TodocListFragmentBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(TasksViewModel.class);
        init();
        View view = vb.getRoot();
        setHasOptionsMenu(true);
        return view;
    }

    private void init() {

        vb.fabAddTask.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_todocListFragment_to_addTaskFragment);
        });
    }
}
