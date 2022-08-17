package com.example.helloworld;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

public class MyViewModel extends ViewModel {
    private MutableLiveData<Integer> number;
    public LiveData<Integer> getNumber() {
        if (number == null) {
            number = new MutableLiveData<Integer>();
            number.setValue(0);
        }
        return number;
    }

    public void increaseNumber() {
        number.setValue(number.getValue() + 1);
    }

    public void decreaseNumber() {
        number.setValue(number.getValue() - 1);
    }
}
