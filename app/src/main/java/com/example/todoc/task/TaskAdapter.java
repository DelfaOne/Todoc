package com.example.todoc.task;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoc.databinding.ItemTaskBinding;

public class TaskAdapter extends ListAdapter<TaskViewStateItem, TaskAdapter.ViewHolder> {

    ViewHolder.OnDeleteItem onDeleteItem;


    public TaskAdapter(ViewHolder.OnDeleteItem onDeleteItem) {
        super(new DiffUtil.ItemCallback<TaskViewStateItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull TaskViewStateItem oldItem, @NonNull TaskViewStateItem newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull TaskViewStateItem oldItem, @NonNull TaskViewStateItem newItem) {
                return oldItem.getTaskName().equalsIgnoreCase(newItem.getProjectName());
            }
        });

        this.onDeleteItem = onDeleteItem;
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
        holder.bind(getItem(position), onDeleteItem);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTaskBinding vb;

        public ViewHolder(ItemTaskBinding vb) {
            super(vb.getRoot());
            this.vb = vb;
        }


        public void bind(TaskViewStateItem item, OnDeleteItem onDeleteItem) {
            vb.titleTask.setText(item.getTaskName());
            vb.projectName.setText(item.getProjectName());
            vb.projectColor.setBackgroundColor(item.getColorProject());
            vb.deleteButton.setOnClickListener(v -> {
                onDeleteItem.deleteItem(item.getId());
            });
        }

        public interface OnDeleteItem {
            void deleteItem(long taskId);
        }
    }
}
