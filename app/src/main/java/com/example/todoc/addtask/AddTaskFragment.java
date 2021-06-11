package com.example.todoc.addtask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoc.databinding.AddTaskFragmentBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddTaskFragment extends BottomSheetDialogFragment {

    AddTaskFragmentBinding vb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vb = AddTaskFragmentBinding.inflate(inflater, container, false);
        init();
        View view = vb.getRoot();
        setHasOptionsMenu(true);
        return view;
    }

    private void init() {
        //TODO
    }
}
