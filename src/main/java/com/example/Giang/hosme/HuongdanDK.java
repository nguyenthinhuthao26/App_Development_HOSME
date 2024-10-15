package com.example.Giang.hosme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ActivityHuongdanDkBinding;

public class HuongdanDK extends AppCompatActivity {
    ActivityHuongdanDkBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHuongdanDkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvent();
    }

    private void addEvent() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}