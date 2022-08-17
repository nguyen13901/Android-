package com.example.helloworld;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;

public class ListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> numbers;
    public LiveData<ArrayList<String>> getNumbers() {
        if (numbers == null) {
            numbers = new MutableLiveData<ArrayList<String>>();
            ArrayList<String> arr = new ArrayList<String>();
            arr.add("0");
            numbers.setValue(arr);
        }
        return numbers;
    }

    public void addNumber(String number) {
        ArrayList<String> arr = (ArrayList<String>) numbers.getValue();
        arr.add(number);
        numbers.setValue(arr);
    }

    public void delNumber(int index) {
        ArrayList<String> arr = (ArrayList<String>) numbers.getValue();
        arr.remove(index);
        numbers.setValue(arr);
    }

    public void updateNumber(int index, String value) {
        ArrayList<String> arr = (ArrayList<String>) numbers.getValue();
        arr.set(index, value);
        numbers.setValue(arr);
    }
}
