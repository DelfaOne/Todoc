package com.example.todoc.task;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class TaskViewState {

    private final boolean isEmptyStateVisible;

    @NonNull
    private final List<TaskViewStateItem> viewStateItemList;

    public TaskViewState(boolean isEmptyStateVisible,@NonNull List<TaskViewStateItem> viewStateItemList) {
        this.isEmptyStateVisible = isEmptyStateVisible;
        this.viewStateItemList = viewStateItemList;
    }

    public boolean isEmptyStateVisible() {
        return isEmptyStateVisible;
    }

    @NonNull
    public List<TaskViewStateItem> getViewStateItemList() {
        return viewStateItemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskViewState that = (TaskViewState) o;
        return isEmptyStateVisible == that.isEmptyStateVisible &&
                viewStateItemList.equals(that.viewStateItemList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isEmptyStateVisible, viewStateItemList);
    }

    @Override
    public String toString() {
        return "TaskViewState{" +
                "isEmptyStateVisible=" + isEmptyStateVisible +
                ", viewStateItemList=" + viewStateItemList +
                '}';
    }
}
