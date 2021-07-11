package com.example.todoc.addtask;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todoc.ViewModelFactory;
import com.example.todoc.databinding.AddTaskFragmentBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddTaskFragment extends BottomSheetDialogFragment {

    AddTaskFragmentBinding vb;
    AddTaskViewModel vm;
    boolean isRefreshing;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vb = AddTaskFragmentBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AddTaskViewModel.class);
        init();
        View view = vb.getRoot();
        setHasOptionsMenu(true);
        return view;
    }

    private void init() {
        setupObserver();
        setupView();
    }

    private void setupObserver() {
        vm.viewStateLiveData.observe(getViewLifecycleOwner(), addTaskViewState -> {
            setTaskViewState(addTaskViewState);
            showAddButton(addTaskViewState.isButtonEnable());
        });
    }

    private void setupView() {
        //Task
        setTextChange(vb.taskEdit, vm);

        //Project
        setTextChange(vb.projectList, vm);
    }

    private void setTextOnEditText(String text, TextInputEditText on) {
        if (text == null) {
            on.setText("");
            on.setSelection(0);
        } else {
            on.setText(text);
            on.setSelection(text.length());
        }
    }

    private void setErrorOnField(String error, TextInputLayout textInputLayout) {
        textInputLayout.setError(error);
    }

    private void setTaskViewState(AddTaskViewState addTaskViewState) {
        isRefreshing = true;
        setTextOnEditText(addTaskViewState.getTask(), vb.taskEdit);
        setErrorOnField(addTaskViewState.getTaskError(), vb.textFieldTaskName);
        vb.projectList.setText(addTaskViewState.getProject(), false);
        setErrorOnField(addTaskViewState.getProjectError(), vb.textFieldProject);


        isRefreshing = false;
    }

    private void showAddButton(Boolean isClickable) {
        vb.addBtn.setEnabled(isClickable);
    }

    private void setTextChange(EditText editText, AddTaskViewModel addTaskViewModel) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isRefreshing) {
                    if (vb.taskEdit.equals(editText)) {
                        addTaskViewModel.onTaskNameChange(s.toString());
                    } else if (vb.projectList.equals(editText)) {
                        addTaskViewModel.onProjectChange(s.toString());
                    }
                }
            }
        });
    }


}
