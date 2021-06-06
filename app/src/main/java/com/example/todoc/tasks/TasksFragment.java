package com.example.todoc.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

        vb.fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.onCLicked();
            }
        });
    }
}
