package com.example.todoc.taskselector;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.todoc.ViewModelFactory;
import com.example.todoc.databinding.TaskSelectorFragmentBinding;

public class TaskSelectorDialogFragment extends DialogFragment implements TaskSelectorAdapter.ViewHolder.OnCheckedListener {

    private TaskSelectorFragmentBinding vb;

    private TaskSelectorViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(TaskSelectorViewModel.class);

        vb = TaskSelectorFragmentBinding.inflate(getLayoutInflater());

        TaskSelectorAdapter taskSelectorAdapter = new TaskSelectorAdapter(this);
        vb.roomSelectorRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        vb.roomSelectorRecyclerView.setAdapter(taskSelectorAdapter);

        vm.getTaskSelectorLiveData().observe(
                this,
                taskSelectorViewStates -> taskSelectorAdapter.submitList(taskSelectorViewStates)
        );
        return vb.getRoot();
    }

    @Override
    public void onChecked(boolean isChecked, long projectId) {
        vm.onCheckedChange(isChecked, projectId);
    }
}
