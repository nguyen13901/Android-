package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.helloworld.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String value;
        int index;

        Intent intent = getIntent();
        if (intent != null) {
            index = intent.getIntExtra("index", -1);
            value = intent.getStringExtra("value");
            binding.edtDetail.setText(value);
        } else {
            index = -1;
            value = "";
        }

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("index", index);
                intent.putExtra("value", binding.edtDetail.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}