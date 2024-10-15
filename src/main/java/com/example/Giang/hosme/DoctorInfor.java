package com.example.Giang.hosme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ActivityDoctorInforBinding;

public class DoctorInfor extends AppCompatActivity {

    ActivityDoctorInforBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorInforBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        displayDoctorInfo();
        addEvents();
    }

    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void displayDoctorInfo() {
        Intent intent = getIntent();
        if (intent!=null){

            byte[] doctorImage = intent.getByteArrayExtra("DOCTOR_Image");
            String doctorName = intent.getStringExtra("DOCTOR_Name");
            String doctorDepartmentName = intent.getStringExtra("DOCTOR_DepartmentName");
            String doctorDescription = intent.getStringExtra("DOCTOR_Description");

            binding.txtDoctorNameInfor.setText(doctorName);
            binding.txtDoctorSpecialistInfor.setText(doctorDepartmentName);
            binding.txtDoctorDescriptionInfo.setText(doctorDescription);

            Bitmap bitmap = BitmapFactory.decodeByteArray(doctorImage, 0, doctorImage.length);
            binding.imvDoctorInfor.setImageBitmap(bitmap);

        }
    }
}