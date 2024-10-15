package com.example.MedicalRecord.medicalrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ActivityNotMedicalBinding;

public class NotProfile extends AppCompatActivity {

    ActivityNotMedicalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotMedicalBinding.inflate(getLayoutInflater());
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