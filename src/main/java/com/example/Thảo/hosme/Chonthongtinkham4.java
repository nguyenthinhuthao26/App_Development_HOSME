package com.example.Thảo.hosme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.Giang.hosme.MainActivity;
import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ActivityChonthongtinkham4Binding;

public class Chonthongtinkham4 extends AppCompatActivity {
    ActivityChonthongtinkham4Binding binding;

    public static final String PREFERENCE_NAME = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChonthongtinkham4Binding.inflate(getLayoutInflater());
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
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        String savedData = sharedPreferences.getString("chuyen_khoa", "");
        String savedData1 = sharedPreferences.getString("bac_si", "");
        String savedData2 = sharedPreferences.getString("date", "");
        String savedData3 = sharedPreferences.getString("time", "");
        binding.txtChuyenKhoa.setText(savedData);
        binding.txtBacSi.setText(savedData1);
        binding.txtDate.setText(savedData2);
        binding.txtHour.setText(savedData3);

        binding.lnChuyenkhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chonthongtinkham4.this, Choncosoyte.class);
                startActivity(intent);
            }
        });
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chonthongtinkham4.this, Xacnhanthongtin.class);
                startActivity(intent);
            }
        });

        binding.imvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(Chonthongtinkham4.this);
                dialog.setContentView(R.layout.dialog1);

                Button btnNo = dialog.findViewById(R.id.btnNo);
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Chonthongtinkham4.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                });

                Button btnYes = dialog.findViewById(R.id.btnYes);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
    }

}