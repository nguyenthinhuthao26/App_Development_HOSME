package com.example.Chi.hosme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ActivityMain3Binding;

public class MainActivity2 extends AppCompatActivity {

    ActivityMain3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
    }

    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.linearAddInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Nevigate after choosing "addInfo"
                showaDialog();
            }
        });
    }

    private void showaDialog() {
        Dialog dialog = new Dialog(MainActivity2.this);
        dialog.setContentView(R.layout.activity_dialog_add_profile);

        Button btnOldProfile, btnNewProfile;
        btnOldProfile = dialog.findViewById(R.id.btnOldProfile);
        btnOldProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Nevigate enter code
                Intent intent = new Intent(MainActivity2.this, EnterCodeDK.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        btnNewProfile = dialog.findViewById(R.id.btnNewProfile);
        btnNewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Nevigate to "ADD INFO by manual"
                Intent intent = new Intent(MainActivity2.this, AddProfileDK.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //chinh kich thuoc dialog
        dialog.getWindow().setGravity(Gravity.BOTTOM); // dua dialog xuong duoi
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //loai bo bg trang
    }
}