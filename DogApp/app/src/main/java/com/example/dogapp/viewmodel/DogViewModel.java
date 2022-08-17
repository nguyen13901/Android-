package com.example.dogapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.dogapp.model.DogBreed;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DogViewModel extends AndroidViewModel {
    private MutableLiveData<List<DogBreed>> listDogs;
    private DogsApiService apiService;

    public DogViewModel(@NonNull Application application) {
        super(application);
        apiService = DogsApiService.getInstance(application);
    }

    private void setListDogs() {
        apiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<DogBreed> listDogBreed) {
                        listDogs.setValue(listDogBreed);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG1", e.getMessage());
                    }
                });
    }

    public MutableLiveData<List<DogBreed>> getListDogs() {
        if (listDogs == null) {
            listDogs = new MutableLiveData<List<DogBreed>>();
            setListDogs();
        }
        return listDogs;
    }
}
