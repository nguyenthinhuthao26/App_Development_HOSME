package com.example.Thảo.hosme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.Chi.hosme.PaymentListBill;
import com.example.Giang.hosme.MainActivity;
import com.example.Thảo.adapters.BacSiAdapter;
import com.example.Thảo.models.Bacsi;
import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ActivityChonbacsiBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Chonbacsi extends AppCompatActivity {
    ActivityChonbacsiBinding binding;
    BacSiAdapter bacSiAdapter;
    ArrayList<Bacsi> bacsi;
    public static final String PREFERENCE_NAME = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChonbacsiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        ArrayList<Bacsi> bacsiList = intent.getParcelableArrayListExtra("BacSiList");
        if (bacsiList != null) {
            bacsi = new ArrayList<>(bacsiList);
            bacSiAdapter = new BacSiAdapter(Chonbacsi.this, R.layout.item_bacsi, bacsiList);
            binding.lvBacSi.setAdapter(bacSiAdapter);

            binding.lvBacSi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bacsi selectedBacsi = bacsi.get(position);

                    Intent intent = new Intent(Chonbacsi.this, Chonlich.class);
                    intent.putExtra("LichKham", selectedBacsi.getLichkham());

                    // Lưu giá trị xuống SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("bac_si", selectedBacsi.getBacsiName());
                    editor.apply();

                    startActivity(intent);
                }
            });
        }

    }
    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bacSiAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.imvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(Chonbacsi.this);
                dialog.setContentView(R.layout.dialog1);

                Button btnNo = dialog.findViewById(R.id.btnNo);
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Chonbacsi.this, MainActivity.class);
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