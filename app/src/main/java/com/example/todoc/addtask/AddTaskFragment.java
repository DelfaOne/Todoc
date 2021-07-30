package com.example.todoc.addtask;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;

import com.example.todoc.R;
import com.example.todoc.ViewModelFactory;
import com.example.todoc.data.entity.ProjectEntity;
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

        vm.getProjectEntitiesLiveData().observe(getViewLifecycleOwner(), projectEntities -> {
            ArrayAdapter<ProjectEntity> arrayAdapter = new ArrayAdapter<ProjectEntity>(requireContext(), R.layout.list_item, projectEntities) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    return getCustomView(position, parent);
                }

                private View getCustomView(int position, ViewGroup parent) {
                    TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                    ProjectEntity projectEntity = getItem(position);
                    textView.setText(projectEntity.getProjectName());
                    return textView;
                }

                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    return getCustomView(position, parent);
                }

                @NonNull
                @Override
                public Filter getFilter() {
                    return new Filter() {
                        @Override
                        protected FilterResults performFiltering(CharSequence constraint) {
                            return null;
                        }

                        @Override
                        protected void publishResults(CharSequence constraint, FilterResults results) {
                        }

                        @Override
                        public CharSequence convertResultToString(Object resultValue) {
                            return ((ProjectEntity) resultValue).getProjectName();
                        }
                    };
                }
            };
            vb.projectList.setAdapter(arrayAdapter);
            vb.projectList.setOnItemClickListener((parent, view, position, id) -> {
                vm.onProjectChange(arrayAdapter.getItem(position).getId());
            });
        });
    }

    private void setupView() {
        //Task
        setTextChange(vb.taskEdit, vm);

        //Add btn
        vb.addBtn.setOnClickListener(v ->  {
            vm.onButtonAddClick();
            NavHostFragment.findNavController(this).navigate(R.id.addTaskToListFragment);
        });
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
        setTextOnEditText(addTaskViewState.getTaskDescription(), vb.taskEdit);
        setErrorOnField(addTaskViewState.getTaskError(), vb.textFieldTaskName);
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
                    addTaskViewModel.onTaskNameChange(s.toString());
                }
            }
        });
    }


}
