package com.example.todoc.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoc.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SelectedProjectsIdRepository {

    private final MutableLiveData<List<Long>> idProjectListLiveData = new MutableLiveData<>();
    private final List<Long> idProjectList = new ArrayList<>();

    public SelectedProjectsIdRepository() {
        idProjectListLiveData.setValue(idProjectList);
    }


    public void addId(long id) {
        idProjectList.add(id);
        idProjectListLiveData.setValue(idProjectList);
    }

    public void deleteId(long id) {
        idProjectList.remove(id);
        idProjectListLiveData.setValue(idProjectList);
    }

    public LiveData<List<Long>> getIdProjectListLiveData() {
        return idProjectListLiveData;
    }
}
