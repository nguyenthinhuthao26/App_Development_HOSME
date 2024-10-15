package com.example.Chi.hosme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ActivityMoMoBinding;

public class MoMo extends AppCompatActivity {
    ActivityMoMoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivityMoMoBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());
       addEvents();
    }

    private void addEvents() {

        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String gia_dv = sharedPreferences.getString("gia_dv", "");

        binding.edtPrice.setText(gia_dv);

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoMo.this, ConfirmMoMo.class);
                intent.putExtra("price", binding.edtPrice.getText().toString());
                intent.putExtra("note", binding.edtNote.getText().toString());
                startActivity(intent);
            }
        });

        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}