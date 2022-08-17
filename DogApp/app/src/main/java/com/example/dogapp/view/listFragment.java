package com.example.dogapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp.R;
import com.example.dogapp.model.DogBreed;
import com.example.dogapp.viewmodel.DogViewModel;
import com.example.dogapp.viewmodel.DogsApiService;
import com.example.dogapp.viewmodel.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class listFragment extends Fragment {
    private DogsApiService apiService;
    private ArrayList<DogBreed> dogBreeds;
    private ArrayList<DogBreed> findList;
    private ItemAdapter itemAdapter;
    private DogViewModel dogViewModel;
    private RecyclerView rvDogs;

    @Override
    public void onCreateOptionsMenu(@androidx.annotation.NonNull Menu menu, @androidx.annotation.NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tìm kiếm loại chó bạn mong mu");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                itemAdapter.findByName(findList, newText);
                return false;
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        rvDogs = view.findViewById(R.id.rv_Dogs);
        dogBreeds = new ArrayList<DogBreed>();
        findList = new ArrayList<DogBreed>();
        itemAdapter = new ItemAdapter(dogBreeds);
        rvDogs.setAdapter(itemAdapter);
        dogViewModel = new ViewModelProvider(this).get(DogViewModel.class);
        dogViewModel.getListDogs().observe(getViewLifecycleOwner(), new Observer<List<DogBreed>>() {
            @Override
            public void onChanged(List<DogBreed> dogBreeds) {
                itemAdapter.setDogBreeds(dogBreeds);
            }
        });

        apiService = DogsApiService.getInstance(getContext());
        apiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@NonNull List<DogBreed> listDogBreed) {
                        findList.addAll(listDogBreed);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("DEBUG1", e.getMessage());
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }
}