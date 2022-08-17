package com.example.helloworld;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.helloworld.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MyViewModel model;
    private ListViewModel listViewModel;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,
                arrayList);
        binding.lvCount.setAdapter(arrayAdapter);

        model = new ViewModelProvider(this).get(MyViewModel.class);
        model.getNumber().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.tvCount.setText("" + integer);
            }
        });

        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        listViewModel.getNumbers().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                if (arrayList.equals(strings) == false) {
                    arrayList.clear();
                    for (int i = 0; i < strings.size(); i++) {
                        arrayList.add(strings.get(i));
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });

        binding.btnDown.setOnClickListener(countDownOnClick);

        binding.lvCount.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                listViewModel.delNumber(i);
                return false;
            }
        });

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            int index = data.getIntExtra("index", -1);
                            String value = data.getStringExtra("value");
                            listViewModel.updateNumber(index, value);
                        }
                    }
                }
        );

        binding.lvCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("index", i);
                intent.putExtra("value", arrayList.get(i));
                resultLauncher.launch(intent);
            }
        });
    }

    private final View.OnClickListener countDownOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            model.decreaseNumber();
            listViewModel.addNumber(binding.tvCount.getText().toString());
        }
    };

    public void countUpOnClick(View view) {
        if (view == binding.btnUp) {
            model.increaseNumber();
            listViewModel.addNumber(binding.tvCount.getText().toString());
        }
    }
}