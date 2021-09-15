package com.example.todoc.taskselector;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoc.databinding.TaskSelectorItemBinding;

public class TaskSelectorAdapter extends ListAdapter<TaskSelectorViewState, TaskSelectorAdapter.ViewHolder> {

    ViewHolder.OnCheckedListener onCheckedListener;

    public TaskSelectorAdapter(ViewHolder.OnCheckedListener onCheckedListener) {
        super(new DiffUtil.ItemCallback<TaskSelectorViewState>() {
            @Override
            public boolean areItemsTheSame(@NonNull TaskSelectorViewState oldItem, @NonNull TaskSelectorViewState newItem) {
                return oldItem.getProjectName().equalsIgnoreCase(newItem.getProjectName());
            }

            @Override
            public boolean areContentsTheSame(@NonNull TaskSelectorViewState oldItem, @NonNull TaskSelectorViewState newItem) {
                return oldItem.isSelected() == newItem.isSelected();
            }
        });
        this.onCheckedListener = onCheckedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskSelectorItemBinding taskSelectorItemBinding = TaskSelectorItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new TaskSelectorAdapter.ViewHolder(taskSelectorItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), onCheckedListener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TaskSelectorItemBinding vb;

        public ViewHolder(TaskSelectorItemBinding vb) {
            super(vb.getRoot());
            this.vb = vb;
        }

        public void bind(TaskSelectorViewState item, OnCheckedListener onCheckedListener) {
            vb.taskSelectorCheckbox.setOnCheckedChangeListener(null);
            vb.taskSelectorCheckbox.setText(item.getProjectName());
            vb.taskSelectorCheckbox.setSelected(item.isSelected());
            vb.taskSelectorCheckbox.setChecked(item.isSelected());
            vb.taskSelectorCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                onCheckedListener.onChecked(item.isSelected(), item.getId());
            });
        }


        public interface OnCheckedListener {
            void onChecked(boolean checked, long projectId);
        }
    }

}
