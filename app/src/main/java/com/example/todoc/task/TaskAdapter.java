package com.example.todoc.task;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoc.databinding.ItemTaskBinding;

public class TaskAdapter extends ListAdapter<TaskUiModel, TaskAdapter.ViewHolder> {


    public TaskAdapter() {
        super(new DiffUtil.ItemCallback<TaskUiModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull TaskUiModel oldItem, @NonNull TaskUiModel newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull TaskUiModel oldItem, @NonNull TaskUiModel newItem) {
                return oldItem.getTaskName().equalsIgnoreCase(newItem.getProjectName());
                //TODO
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskBinding itemTaskBinding = ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new ViewHolder(itemTaskBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTaskBinding vb;

        public ViewHolder(ItemTaskBinding vb) {
            super(vb.getRoot());
            this.vb = vb;
        }


        public void bind(TaskUiModel item) {
            vb.titleTask.setText(item.getTaskName());
            vb.projectName.setText(item.getProjectName());
            vb.colorProject.setBackgroundColor(item.getColorProject());
        }
    }
}
