package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;

    private final String[] btnNames = {
            "7", "8", "9", "+/-", "←",
            "4", "5", "6", "×", "÷",
            "1", "2", "3", "-",
            "C", "0", ".", "+", "="
    };
    private String expression = "";
    private Boolean isEqual = false;
    private final Handle h = new Handle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        createListBtn();
    }

    private void createListBtn() {
        Button btn;
        for (int i = 0; i < 10; i++) {
            btn = new Button(this);
            btn.setText(btnNames[i]);
            btn.setTextSize(25);
            btn.setId(i);
            btn.setOnClickListener(this);
            binding.grl1.addView(btn, 200, 200);
        }
        for (int i = 10; i < btnNames.length - 1; i++) {
            btn = new Button(this);
            btn.setText(btnNames[i]);
            btn.setTextSize(25);
            btn.setId(i);
            btn.setOnClickListener(this);
            binding.grl3.addView(btn, 200, 200);
        }
        btn = new Button(this);
        btn.setText(btnNames[btnNames.length - 1]);
        btn.setTextSize(25);
        btn.setId(btnNames.length - 1);
        btn.setOnClickListener(this);
//        btn.setBackgroundColor(Color.parseColor("#025cb2"));
        binding.grl4.addView(btn, 200, 400);
    }

    @Override
    public void onClick(View view) {
        if (isEqual == true) {
            binding.edtResult.setText("");
            binding.tvLog.setText("");
            expression = "";
            isEqual = false;
        }
        Double result;
        String temp;
        int id = view.getId();
        switch (id) {
            case 0:
            case 1:
            case 2:
            case 5:
            case 6:
            case 7:
            case 10:
            case 11:
            case 12:
            case 15:
            case 16:
                binding.edtResult.append(btnNames[id]);
                break;
            case 3:
                temp = binding.edtResult.getText().toString();
                expression = "(1-2)*(" + expression + temp + ")";
                binding.tvLog.setText("-(" + binding.tvLog.getText() + temp + ")");
                break;
            case 4:
                temp = binding.edtResult.getText().toString();
                if (temp != null && temp.length() > 0) {
                    binding.edtResult.setText(temp.substring(0, temp.length() -1));
                }
                break;
            case 8:
                binding.tvLog.append(binding.edtResult.getText().toString() + btnNames[id]);
                expression = expression + binding.edtResult.getText().toString() + "*";
                binding.edtResult.setText("");
                break;
            case 9:
                binding.tvLog.append(binding.edtResult.getText().toString() + btnNames[id]);
                expression = expression + binding.edtResult.getText().toString() + "/";
                binding.edtResult.setText("");
                break;
            case 13:
            case 17:
                temp = binding.edtResult.getText().toString() + btnNames[id];
                binding.tvLog.append(temp);
                expression = expression + temp;
                binding.edtResult.setText("");
                break;
            case 14:
                binding.edtResult.setText("");
                binding.tvLog.setText("");
                expression = "";
                break;
            case 18:
                if (expression != "") {
                    isEqual = true;
                    temp = binding.edtResult.getText().toString();
                    binding.tvLog.append(temp + btnNames[id]);
                    expression = expression + temp;
                    result = h.valueMath(expression);
                    if (result != Double.NaN) {
                        if (Math.round(result) == result) {
                            binding.edtResult.setText("" + Math.round(result));
                            binding.tvLog.append("" + Math.round(result));
                        } else {
                            binding.edtResult.setText("" + result);
                            binding.tvLog.append("" + result);
                        }
                    } else {
                        binding.edtResult.setText("Err");
                    }
                }
                break;
        }
    }
}