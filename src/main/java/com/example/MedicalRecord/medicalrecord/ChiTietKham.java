package com.example.MedicalRecord.medicalrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.Giang.hosme.MainActivity;
import com.example.MedicalRecord.adapters.DetailResultAdapter;
import com.example.MedicalRecord.models.DetailResult;
import com.example.Trân.hosme.MainActivity3;
import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ActivityChiTietKhamBinding;

import java.util.ArrayList;
import java.util.List;

public class ChiTietKham extends AppCompatActivity {
    ActivityChiTietKhamBinding binding;
    DetailResultAdapter adapter;
    public static final String DB_NAME = "database_db.sqlite";
    public static final String DB_FOLDER = "/databases";
    public static SQLiteDatabase db = null;
    public static final String TBL_NAME = "TestData";
    public static final String COL_MEDICALID = "MedicalID";
    public static final String COL_TESTID = "TestID";
    public static final String COL_PATIENTID = "PatienID";
    public static final String COL_PATIENTNAME = "PatienName";
    public static final String COL_MAINEXAM = "MainExam";
    public static final String COL_TIMEIN = "DateTimeIn";
    public static final String COL_TIMEOUT = "DateTimeOut";
    public static final String COL_ADDRESS = "AddressHos";
    public static final String COL_IMAGE = "Image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChiTietKhamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getData();
        initData();
        addEvents();
    }

    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.imvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietKham.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private List<DetailResult> loadData() {
        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        String[] projection = null;
        String selection = COL_PATIENTID + " = ?";
        String[] selectionArgs = {patientId};

//        Cursor cursor = db.rawQuery("SELECT MedicalRecor.*, PatienProfile.DateofBirth, PatienProfile.Gender, PatienProfile.PhoneNumber FROM BookingData INNER JOIN PatienProfile ON BookingData.PatienID = PatienProfile.PatienID", null);
        Cursor cursor = db.query(TBL_NAME, projection, selection, selectionArgs, null, null, null);

        List<DetailResult> detailResults = new ArrayList<>();
//        Cursor cursor = db.query(TBL_NAME, projection, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            DetailResult dr = new DetailResult(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getBlob(7), cursor.getString(8), cursor.getString(9));

            detailResults.add(dr);
        }




        cursor.close();
        return detailResults;
    }

    private void initData() {
        if (patientId != null) {
            List<DetailResult> detailResults = loadData();
            adapter = new DetailResultAdapter(this, R.layout.item_chi_tiet, detailResults);
            binding.lvChitiet.setAdapter(adapter);
        } else {

        }
    }


    String patientId;
    private void getData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("patientId")) {
            patientId = intent.getStringExtra("patientId");
        } else {
            // Xử lý trường hợp không có giá trị "patientId" được gửi qua Intent
            Log.e("ChiTietKham", "Không có patientId được truyền qua Intent");
        }
    }
}