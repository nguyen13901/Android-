package com.example.controlwifi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ApiService apiService;

    Button btnLeft;
    Button btnRight;
    TextView tvSpeed;
    Slider sldSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLeft = findViewById(R.id.btn_left);
        btnRight = findViewById(R.id.btn_right);
        tvSpeed = findViewById(R.id.tv_speed);
        sldSpeed = findViewById(R.id.sld_speed);

        apiService = ApiService.getInstance(getApplicationContext());

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    apiService.getSpeed()
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableSingleObserver<Speed>() {
                                @Override
                                public void onSuccess(@NonNull Speed speed) {
                                    tvSpeed.setText(speed.speed);
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    Log.d("DEBUG1", e.getMessage());
                                }
                            });
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        sldSpeed.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@androidx.annotation.NonNull Slider slider, float value, boolean fromUser) {
                Log.d("DEBUG1", "onValueChange: " + Math.round(value));
                apiService.control(Integer.toString(Math.round(value)))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Status>() {
                            @Override
                            public void onSuccess(@NonNull Status status) {
                                Log.d("DEBUG1", "onSuccess: " + status.status);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d("DEBUG1", "onError: " + e.getMessage());
                            }
                        });
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG1", "onClick: left");
                apiService.left()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Status>() {
                            @Override
                            public void onSuccess(@NonNull Status status) {
                                Log.d("DEBUG1", "onSuccess: " + status.status);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d("DEBUG1", "onError: " + e.getMessage());
                            }
                        });
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG1", "onClick: left");
                apiService.right()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Status>() {
                            @Override
                            public void onSuccess(@NonNull Status status) {
                                Log.d("DEBUG1", "onSuccess: " + status.status);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d("DEBUG1", "onError: " + e.getMessage());
                            }
                        });
            }
        });
    }
}