package com.example.Trân.hosme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.Chi.models.Profile;
import com.example.Giang.hosme.MainActivity;
import com.example.MedicalRecord.adapters.ProfileAdapter;
import com.example.MedicalRecord.medicalrecord.ChooseProfile;
import com.example.MedicalRecord.medicalrecord.NotProfile;
import com.example.Thảo.hosme.Choncosoyte;
import com.example.Trân.adapter.HealthFormAdapter;
import com.example.Trân.model.HealthForm;
import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.Activity3Binding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    public static final String DB_NAME = "database_db.sqlite";
    public static final String DB_FOLDER = "databases";
    public static SQLiteDatabase db = null;
    public static final String TBL_NAME = "BookingData";
    public static final String COL_PAID = "PatienID";
    public static final String COL_PANAME = "PatienName";
    public static final String COL_DEID = "DepartmentID";
    public static final String COL_DENAME = "DepartmentName";
    public static final String COL_ADDRESSHOS = "AddressHos";
    public static final String COL_QUEUE = "Queue";
    public static final String COL_ROOM = "Room";
    public static final String COL_DATE = "Date";
    public static final String COL_TIME = "Time";
    public static final String COL_STATUS = "Status";

    Activity3Binding binding;
    HealthForm selectedForm = null;
    HealthFormAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
        initAdapter();
    }

    private void initAdapter() {
        List<HealthForm> healthForms = loadDataFromDatabase();
        List<HealthForm> healthForm = loadData();

        List<HealthForm> combinedList = new ArrayList<>();
        combinedList.addAll(healthForms);
        combinedList.addAll(healthForm);

        Collections.reverse(combinedList);

        if (combinedList.isEmpty()) {
            Intent intent = new Intent(MainActivity3.this, MainActivity10.class);
            startActivity(intent);
            finish();
        } else {
            adapter = new HealthFormAdapter(this, R.layout.item_health_form, combinedList);
            binding.lvHealthForm.setAdapter(adapter);
        }
    }

    private List<HealthForm> loadDataFromDatabase() {
        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String userID = sharedPreferences.getString("userId", "");

//        Cursor cursor = db.rawQuery("SELECT * FROM " + TBL_NAME + " WHERE " + USER_ID + " = ?", new String[]{userID});
        Cursor cursor = db.rawQuery("SELECT BookingData.*, PatientProfile.DateofBirth, PatientProfile.Gender, PatientProfile.PhoneNumber FROM BookingData INNER JOIN PatientProfile ON BookingData.PatienID = PatientProfile.PatientID WHERE PatientProfile.UserID = ?", new String[]{userID});
        List<HealthForm> healthForms = new ArrayList<>();
        while (cursor.moveToNext()) {

            // Tạo một đối tượng HealthForm từ dữ liệu trong Cursor
            HealthForm h = new HealthForm(cursor.getInt(0), cursor.getInt(1),
                    cursor.getString(2), cursor.getString(3).toUpperCase(),
                    cursor.getInt(4), cursor.getString(5),
                    cursor.getString(6), cursor.getInt(7),
                    cursor.getString(8), cursor.getString(9),
                    cursor.getString(10), cursor.getString(11),
                    cursor.getString(12),
                    cursor.getString(13), cursor.getString(14));
            healthForms.add(h);
        }

        cursor.close();
        return healthForms;
    }


    private List<HealthForm> loadData() {
        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String selectedPatientID = sharedPreferences.getString("patientID", "");
        String selectedDate = sharedPreferences.getString("date", "");
        String selectedChuyenkhoa = sharedPreferences.getString("chuyen_khoa", "");

        Cursor cursor1 = db.rawQuery("SELECT BookingData.*, PatientProfile.DateofBirth, PatientProfile.Gender, PatientProfile.PhoneNumber " +
                "FROM BookingData INNER JOIN PatientProfile ON BookingData.PatienID = PatientProfile.PatientID " +
                "WHERE BookingData.PatienID = ? AND BookingData.Date = ? AND BookingData.DepartmentName = ?", new String[]{selectedPatientID, selectedDate,selectedChuyenkhoa});

        List<HealthForm> healthForm = new ArrayList<>();
        while (cursor1.moveToNext()) {
            String patientID = cursor1.getString(2); // Lấy giá trị của cột PatienID trong Cursor

            boolean isMatched = false; // Biến boolean để kiểm tra khớp

            // Lặp qua danh sách đã được tải từ loadData()
            for (HealthForm healthForms : loadDataFromDatabase()) {
                if (patientID.equals(healthForms.getPatientID())) {
                    isMatched = true; // Có khớp, đánh dấu là true
                    break; // Thoát khỏi vòng lặp khi đã tìm thấy khớp
                }
            }

            // Nếu không khớp, tạo một đối tượng HealthForm từ dữ liệu trong Cursor và thêm vào danh sách
            if (!isMatched) {
                HealthForm h = new HealthForm(cursor1.getInt(0), cursor1.getInt(1),
                        cursor1.getString(2), cursor1.getString(3).toUpperCase(),
                        cursor1.getInt(4), cursor1.getString(5),
                        cursor1.getString(6), cursor1.getInt(7),
                        cursor1.getString(8), cursor1.getString(9),
                        cursor1.getString(10), cursor1.getString(11),
                        cursor1.getString(12),
                        cursor1.getString(13), cursor1.getString(14));
                healthForm.add(h);
            }
        }
        cursor1.close();
        return healthForm;
    }



    private void addEvents() {
        binding.lvHealthForm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedForm = (HealthForm) adapter.getItem(position);
                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
//            Attach data
                if (selectedForm != null){
                    intent.putExtra("data", selectedForm);
                    startActivity(intent);
                }
            }
        });

        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.imvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

}